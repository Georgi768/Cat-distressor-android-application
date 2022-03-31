package com.example.androidapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidapplication.R
import com.example.androidapplication.factory.Animal

class CatsAdapter(private val context: Context, val mCats: ArrayList<Animal>) :
    RecyclerView.Adapter<CatsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.activity_collection_cat,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = mCats.get(position)
        holder.catItem.text = items.breed // holder.catItem.setImageURI(items.imageURL.toUri())
        Glide.with(context).load(items.imageURL).into(holder.catImage)
    }

    override fun getItemCount(): Int {
        return mCats.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catImage = itemView.findViewById<ImageView>(R.id.c_image)
        val catItem = itemView.findViewById<TextView>(R.id.tv_item_name)
    }
}