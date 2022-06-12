package com.jn.capstoneproject.d_jahit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jn.capstoneproject.d_jahit.diffcallback.DiffCallback
import com.jn.capstoneproject.d_jahit.databinding.ItemPakaianHorizontalBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem

class ListProducthorizontalAdapter:RecyclerView.Adapter<ListProducthorizontalAdapter.ListViewHolder>() {
    private val listProduct=ArrayList<ProductsItem>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setAllData(data: List<ProductsItem>) {
        val diffCallback = DiffCallback(this.listProduct, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listProduct.clear()
        this.listProduct.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }



    inner class ListViewHolder(private val binding: ItemPakaianHorizontalBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: ProductsItem) {
            binding.apply {
                tvNameProduk.text = data.name
                tvDescription.text = data.definition
                Glide.with(itemView)
                    .load(data.productPhoto)
                    .into(imgProduct)
            }
////
            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListProducthorizontalAdapter.ListViewHolder {
        val view= ItemPakaianHorizontalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListProducthorizontalAdapter.ListViewHolder, position: Int) {
        holder.bind(listProduct[position])
    }
    override fun getItemCount(): Int = listProduct.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ProductsItem) {
        }

    }



}