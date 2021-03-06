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
import com.example.androidapplication.databaseManagement.DBHelper
import com.example.androidapplication.factory.Animal

class SpyAdapter(private val spyID: Int, private val context: Context, val mCats: ArrayList<Animal>) : RecyclerView.Adapter<SpyAdapter.ViewHolder>() {
    private var dbHelper: DBHelper = DBHelper(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            // Inflate catItem
            LayoutInflater.from(context).inflate(
                R.layout.activity_collection_cat,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = mCats.get(position)
        holder.catItem.text = items.breed

        //Display image of cat in catItem
        Glide.with(context).load(items.imageURL).into(holder.catImage)

        //Set listener for clicking on cat in the recyclerView
        holder.parentLayout.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            // Set Title and button text
            builder.setTitle("Steal cat? ${items.id}")
            builder.setPositiveButton("Yes") { _, _ ->
                Toast.makeText(context, "You have saved the cat", Toast.LENGTH_LONG).show()
                dbHelper.addCatInUserCollection(spyID, items.imageURL)
            }
            builder.setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(context, "clicked cancel\n operation cancel", Toast.LENGTH_LONG)
                    .show()
            }
            builder.setNegativeButton("No") { _, _ ->
                Toast.makeText(context, "clicked No", Toast.LENGTH_LONG).show()
            }
            // Build dialog
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