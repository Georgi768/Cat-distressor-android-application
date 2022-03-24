package com.example.androidapplication.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.adapters.CatsAdapter
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.factory.Cat

class CollectionActivity : AppCompatActivity() {
    private lateinit var cats: ArrayList<Animal>
    private lateinit var catImageView: ImageView
    private lateinit var button: Button
    private lateinit var cat_view : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        cats = ArrayList()
        cat_view = findViewById(R.id.cat_view)

        cat_view.layoutManager = LinearLayoutManager(this)
        val adapter = CatsAdapter(this,generateAnimal())

        cat_view.adapter = adapter
    }

    private fun generateAnimal() : ArrayList<Animal>
    {
        for (i in 1..5)
        {
            cats.add(Cat(i,"Breed","description","https:\\/\\/cdn2.thecatapi.com\\/images\\/cap.jpg"))
        }
        return cats
    }
}