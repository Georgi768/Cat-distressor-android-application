package com.example.androidapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.factory.Animal

class CatsAdapter (val context: Context, val mCats: ArrayList<Animal>) : RecyclerView.Adapter<CatsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.cat_item,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        val items = mCats.get(position)
        holder.catItem.setImageURI(items.imageURL.toUri())
    }

    override fun getItemCount(): Int {
        return mCats.size
    }
    fun addCat(model: Animal) {
        mCats.add(model)
        // notifyDataSetChanged() // this method is costly I avoid it whenever possible
        notifyItemInserted(mCats.size)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catItem = itemView.findViewById<ImageView>(R.id.cat_image_holder)
    }
}