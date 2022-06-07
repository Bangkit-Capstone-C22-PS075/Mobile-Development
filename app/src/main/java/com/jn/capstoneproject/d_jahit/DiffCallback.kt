package com.jn.capstoneproject.d_jahit

import androidx.recyclerview.widget.DiffUtil
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductResponse
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem

class DiffCallback(
    private val mOldFavList: List<ProductsItem>,
    private val mNewFavList: List<ProductsItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = mOldFavList.size

    override fun getNewListSize() = mNewFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.id == newEmployee.id
    }
}