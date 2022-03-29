package com.example.androidapplication.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidapplication.R
import com.example.androidapplication.factory.Animal

class SpyAdapter (private val context: Context, val mCats: ArrayList<Animal>) : RecyclerView.Adapter<SpyAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
            R.layout.activity_collection_user,
            viewGroup,
            false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder,position: Int) {
        val items = mCats.get(position)
        holder.catItem.text = items.description // holder.catItem.setImageURI(items.imageURL.toUri())
        Glide.with(context).load(items.imageURL).into(holder.catImage)
        holder.parentLayout.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Steal cat? ${items.id}")
            builder.setPositiveButton("Yes"){ _, _ ->

                Toast.makeText(context,"clicked yes",Toast.LENGTH_LONG).show()
            }
            builder.setNeutralButton("Cancel"){ _, _ ->
                Toast.makeText(context,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("No"){ _, _ ->
                Toast.makeText(context,"clicked No",Toast.LENGTH_LONG).show()
            }
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return mCats.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parentLayout = itemView.findViewById<LinearLayout>(R.id.parent)
        val catImage = itemView.findViewById<ImageView>(R.id.c_image)
        val catItem = itemView.findViewById<TextView>(R.id.tv_item_name)
    }
}