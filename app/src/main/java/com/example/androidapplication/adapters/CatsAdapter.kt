package com.example.androidapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.models.Cat

class CatsAdapter (private val mCats: ArrayList<Cat>) : RecyclerView.Adapter<CatsAdapter.ViewHolder>(){
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.cat_item, viewGroup, false)
        val holder = ViewHolder(view)
        holder.catImageView.setOnClickListener {
            it.isVisible = false
        }
        return holder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.catImageView.setImageURI(mCats[position].url.toUri())
    }

    override fun getItemCount(): Int {
        return mCats.size
    }
    fun addCat(model: Cat) {
        mCats.add(model)
        // notifyDataSetChanged() // this method is costly I avoid it whenever possible
        notifyItemInserted(mCats.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catImageView = itemView.findViewById<ImageView>(R.id.cat_image_holder)!!
    }
}