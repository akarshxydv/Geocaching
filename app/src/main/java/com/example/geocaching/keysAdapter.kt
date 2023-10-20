package com.example.geocaching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class keysAdapter(val keylist:ArrayList<qrInfo>):RecyclerView.Adapter<keysAdapter.viewHolder>() {


    class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val keys: TextView =itemView.findViewById(R.id.keyName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): keysAdapter.viewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.keysitem,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: keysAdapter.viewHolder, position: Int) {
            val keynote=keylist[position]
        holder.keys.text=keynote.keys
    }

    override fun getItemCount(): Int {
            return keylist.size
    }
}