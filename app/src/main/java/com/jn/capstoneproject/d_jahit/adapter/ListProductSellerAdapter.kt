package com.jn.capstoneproject.d_jahit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jn.capstoneproject.d_jahit.databinding.ItemPakaianHorizontalBinding
import com.jn.capstoneproject.d_jahit.databinding.ItemProductBinding
import com.jn.capstoneproject.d_jahit.diffcallback.DiffCallback
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem

class ListProductSellerAdapter: RecyclerView.Adapter<ListProductSellerAdapter.ViewHolder>() {
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




    inner class ViewHolder (private val binding: ItemProductBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductsItem){
            binding.apply {
                tvNameProduk.text = data.name
                tvDestination.text = data.definition
                tvPrice1.text=data.price1.toString().trim()
                tvPrice2.text=data.price2.toString().trim()
                Glide.with(itemView)
                    .load(data.productPhoto)
                    .into(imgProduk)
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
    ): ListProductSellerAdapter.ViewHolder {
        val view= ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListProductSellerAdapter.ViewHolder, position: Int) {
        holder.bind(listProduct[position])
    }

    override fun getItemCount(): Int = listProduct.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ProductsItem) {
        }

    }
}