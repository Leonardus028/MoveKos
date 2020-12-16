package com.example.movekos.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movekos.R

class ListOrderAdapter(private val listOrder: ArrayList<Order>) : RecyclerView.Adapter<ListOrderAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_item, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val order = listOrder[position]
        holder.tvBarang.text = order.barang
        holder.tvJarak.text = order.jarak
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listOrder[holder.adapterPosition])
//            val position = holder.adapterPosition.toString()
        }

    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvBarang: TextView = itemView.findViewById(R.id.tvBarang)
        var tvJarak: TextView = itemView.findViewById(R.id.tvJarak)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Order)

    }
}
