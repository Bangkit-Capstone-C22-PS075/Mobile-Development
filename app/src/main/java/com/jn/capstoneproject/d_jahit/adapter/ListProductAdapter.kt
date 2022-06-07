package com.jn.capstoneproject.d_jahit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jn.capstoneproject.d_jahit.DiffCallback
import com.jn.capstoneproject.d_jahit.databinding.ItemPakaianBinding
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem

class ListProductAdapter: RecyclerView.Adapter<ListProductAdapter.ListViewHolder>() {
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



    inner class ListViewHolder(private val binding: ItemPakaianBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: ProductsItem) {
            binding.apply {
                tvNameProduk.text = data.name
                tvDescription.text = data.definition
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
    ): ListProductAdapter.ListViewHolder {
        val view=ItemPakaianBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listProduct[position])
    }

    override fun getItemCount(): Int = listProduct.size
    interface OnItemClickCallback {
        fun onItemClicked(data: ProductsItem) {
        }


    }

}