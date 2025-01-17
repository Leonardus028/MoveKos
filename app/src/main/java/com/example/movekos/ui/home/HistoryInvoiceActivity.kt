package com.example.movekos.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movekos.HomeActivity
import com.example.movekos.HowtoOrderActivity
import com.example.movekos.R
import kotlinx.android.synthetic.main.invoice.*

class HistoryInvoiceActivity: AppCompatActivity() {

    companion object{
        const val EXTRA_ORDER = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.invoice)

        val order = intent.getSerializableExtra(EXTRA_ORDER) as Order
        showDetail(order)

        super.onCreate(savedInstanceState)

        OrderNow.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

    }

    private fun showDetail(order: Order){
        barang.text = order.barang
        jarak.text = order.jarak
        harga.text = order.harga
        norek.text = order.rekening
        tvResi.text = order.noresi
        status.text = order.status
    }

}