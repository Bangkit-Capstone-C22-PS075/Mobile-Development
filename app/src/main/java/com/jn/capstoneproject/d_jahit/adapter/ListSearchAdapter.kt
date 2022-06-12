package com.jn.capstoneproject.d_jahit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jn.capstoneproject.d_jahit.diffcallback.DiffCallbackSearch
import com.jn.capstoneproject.d_jahit.databinding.ItemPakaianBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellerProductItem

class ListSearchAdapter: RecyclerView.Adapter<ListSearchAdapter.ListViewHolder>() {
    private val listProduct=ArrayList<SellerProductItem>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setAllData(data: List<SellerProductItem>) {
        val diffCallback = DiffCallbackSearch(this.listProduct, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listProduct.clear()
        this.listProduct.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: ItemPakaianBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: SellerProductItem) {
            binding.apply {
                tvNameProduk.text = data.name
                tvDescription.text = data.definition
                Glide.with(itemView)
                    .load(data.productPhoto)
                    .into(imgProduct)
            }

            itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListSearchAdapter.ListViewHolder {
        val view= ItemPakaianBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listProduct[position])
    }

    override fun getItemCount(): Int {
        if(listProduct.size !=null){
            return listProduct.size
        }
        return 10
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SellerProductItem) {
        }


    }

}