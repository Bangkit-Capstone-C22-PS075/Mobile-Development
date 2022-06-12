package com.jn.capstoneproject.d_jahit.diffcallback

import androidx.recyclerview.widget.DiffUtil
import com.jn.capstoneproject.d_jahit.model.dataresponse.ProductsItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellerProductItem
import com.jn.capstoneproject.d_jahit.model.dataresponse.SellersItem

class DiffCallbackSearch (
    private val mOldFavList: List<SellerProductItem>,
    private val mNewFavList: List<SellerProductItem>
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