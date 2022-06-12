package com.jn.capstoneproject.d_jahit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jn.capstoneproject.d_jahit.R
import com.jn.capstoneproject.d_jahit.databinding.ItemHistoryBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem

class HistoryAdapter (private val history: ArrayList<ProductsItem>):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {


    class HistoryViewHolder(private val binding:ItemHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data :ProductsItem){
            binding.apply {
                Glide.with(itemView)
                    .load(data.productPhoto)
                    .into(imgProduk)
                tvNameProduk.text=data.name
                tvDestination.text=data.definition
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        val view=ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HistoryViewHolder(view)
    }
    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
         holder.bind(history[position])
    }

    override fun getItemCount(): Int =history.size

    fun setData(list: List<ProductsItem>){
        history.clear()
        history.addAll(list)
        notifyDataSetChanged()
    }
}