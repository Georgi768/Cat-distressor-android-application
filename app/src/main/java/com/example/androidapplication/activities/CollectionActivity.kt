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
import com.example.androidapplication.iterator.Aggregate
import com.example.androidapplication.iterator.Collection
import com.example.androidapplication.iterator.TraverseInOrder

class CollectionActivity : AppCompatActivity() {
    private lateinit var cats: ArrayList<Animal>
    private lateinit var cat_view : RecyclerView
    private lateinit var aggregate : Collection
    private lateinit var inOrderButton : Button
    private lateinit var  backBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        inOrderButton = findViewById(R.id.InOrderTraversal)
        backBtn = findViewById(R.id.BackwardsTraversal)
        cats = ArrayList()
        cat_view = findViewById(R.id.cat_view)
        aggregate = Aggregate(generateList())

        cat_view.layoutManager = LinearLayoutManager(this)

        val adapter = CatsAdapter(this,traverseInOrderCollection())
        cat_view.adapter = adapter


        inOrderButton.setOnClickListener{
            val adapter = CatsAdapter(this,traverseInOrderCollection())
            cat_view.adapter = adapter
        }

        backBtn.setOnClickListener{
            val adapter = CatsAdapter(this,traverseCollectionInBackwards())

            cat_view.adapter = adapter
        }
    }

    private fun generateList() : ArrayList<Animal>
    {
        val list = ArrayList<Animal>()
        for (i in 1..10)
        {
            list.add(Cat(i,"test","description ${i}","no"))
        }
        return list
    }

    private fun traverseInOrderCollection() : ArrayList<Animal>
    {
        val inOrderCollection = ArrayList<Animal>()
        val iterator = aggregate.createInOrderIterator()
        while (iterator.hasNext()){
            inOrderCollection.add(iterator.next())
        }
        return inOrderCollection
    }

    private fun traverseCollectionInBackwards() : ArrayList<Animal>
    {
        val randomCollection = ArrayList<Animal>()
        val iterator = aggregate.createBackwardsIterator()
        while (iterator.hasNext()){
            randomCollection.add(iterator.next())
        }
        return randomCollection
    }
}