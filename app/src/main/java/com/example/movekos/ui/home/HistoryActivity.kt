package com.example.movekos.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movekos.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistoryActivity: AppCompatActivity() {
    private lateinit var adapter: ListOrderAdapter
    private var list: ArrayList<Order> = arrayListOf()
    private lateinit var recyclerView: RecyclerView

    private var mAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef: DatabaseReference = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_history)

        showRecycleList()
        addOrder(list)

        super.onCreate(savedInstanceState)
    }

    private fun showRecycleList() {
        recyclerView = findViewById(R.id.rvOrder)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ListOrderAdapter(list)
        recyclerView.adapter = adapter
        adapter.setOnItemClickCallback(object : ListOrderAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Order) {

                val intentInvoice = Intent(this@HistoryActivity, HistoryInvoiceActivity::class.java).apply{
                    putExtra(HistoryInvoiceActivity.EXTRA_ORDER,
                        data
                    )
                }
                this@HistoryActivity.startActivity(intentInvoice)
            }
        })
    }

    private fun addOrder(list: ArrayList<Order>) {
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser

        myRef.child("Users").child(currentUser!!.uid).child("record")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for(order in snapshot.children){
                        val orderDetail = Order()
                        orderDetail.barang = order.child("barang").value.toString()
                        orderDetail.destination = order.child("destination").value.toString()
                        orderDetail.jarak = order.child("distance").value.toString()
                        orderDetail.waktu = order.child("duration").value.toString()
                        orderDetail.origin = order.child("origin").value.toString()
                        orderDetail.rekening = order.child("rekening").value.toString()

                        list.add(orderDetail)
                    }
                    Log.d("fbTEST", list[1].barang)
                    Log.d("xxx", list.size.toString())
                    adapter.notifyDataSetChanged()
                }
            })
    }
}