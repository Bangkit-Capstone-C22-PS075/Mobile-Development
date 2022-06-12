package com.jn.capstoneproject.d_jahit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jn.capstoneproject.d_jahit.R

class ComentAdapter: RecyclerView.Adapter<ComentAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentAdapter.ViewHolder {
        var  view=LayoutInflater.from(parent.context).inflate(R.layout.item_coment,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComentAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
       return 10
    }
}