package com.example.movekos.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movekos.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.alert_dialog.view.*
import kotlinx.android.synthetic.main.invoice.*

@Suppress("UNUSED_PARAMETER")
class InvoiceActivity: AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private var strBarang = ""
    private var strOrigin = ""
    private var strDest = ""
    private var strDistance = ""
    private var strDuration = ""
    private var rekening = ""
    private var harga = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.invoice)

        database = FirebaseDatabase.getInstance()
        myRef = database.reference
        inisialisasi()

        super.onCreate(savedInstanceState)
    }

    fun orderClick(view: View) {
        alertDialog()

    }

    private fun pushFirebase(
        origin: String,
        destination: String,
        distance: String,
        harga: String,
        rekening: String,
        barang: String,
        noresi: String,
        status: String){
        val currentUser = mAuth.currentUser
        val recordRef = myRef.child("Users").child(currentUser!!.uid).child("record").child(java.util.Calendar.getInstance().time.toString())
        recordRef.child("origin").setValue(origin)
        recordRef.child("destination").setValue(destination)
        recordRef.child("distance").setValue(distance)
        recordRef.child("harga").setValue(harga)
        recordRef.child("rekening").setValue(rekening)
        recordRef.child("barang").setValue(barang)
        recordRef.child("noresi").setValue(noresi)
        recordRef.child("status").setValue(status)
    }

    private fun alertDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Masukan Rekening")
        val mAlertDialog = mBuilder.show()

        mDialogView.okay.setOnClickListener {
            mAlertDialog.dismiss()
            rekening = mDialogView.edittextnya.text.toString()

            pushFirebase(strOrigin, strDest, strDistance, harga , rekening, strBarang, "-", "Pending")

            Toast.makeText(this, "ORDER DITERIMA", Toast.LENGTH_SHORT).show()

            finish()
        }
        mDialogView.cancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun inisialisasi(){
        val tvBarang: TextView = findViewById(R.id.barang)
        val tvJarak: TextView = findViewById(R.id.jarak)
        val tvHarga: TextView = findViewById(R.id.harga)
        val tvResi: TextView = findViewById(R.id.tvResi)

        val extras = intent.extras

        strBarang = extras!!.getString("EXTRA_BARANG")!!
        strOrigin = extras.getString("EXTRA_ORIGIN")!!
        strDest = extras.getString("EXTRA_DESTINATION")!!
        strDistance = extras.getString("EXTRA_DISTANCE")!!
        strDuration = extras.getString("EXTRA_DURATION")!!

        //harga = (strDistance.substringBefore(".").toInt() * 5000).toString()

        Log.d("EXTRANYA", "$strBarang, $strDistance, $strDuration, $strDest, $strOrigin")

        tvBarang.text = strBarang
        tvJarak.text = strDistance
        tvHarga.text = "-"
        tvResi.text = "-"
        status.text = "Pending"
    }
}