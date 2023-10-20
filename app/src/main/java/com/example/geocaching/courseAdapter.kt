package com.example.geocaching

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class courseAdapter (var listnote:List<ItemCourse>,context: Context): RecyclerView.Adapter<courseAdapter.viewHolder>() {

    class viewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView =itemView.findViewById(R.id.content)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): courseAdapter.viewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.noteitem,parent,false)
        return viewHolder(view)

    }

    override fun onBindViewHolder(holder: courseAdapter.viewHolder, position: Int) {
        val note=listnote[position]
        holder.title.text=note.course
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Addkeys::class.java)
            intent.putExtra("course", note.course)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listnote.size
    }
    fun refresh(newnote:List<ItemCourse>){
        listnote=newnote
        notifyDataSetChanged()
    }

}

