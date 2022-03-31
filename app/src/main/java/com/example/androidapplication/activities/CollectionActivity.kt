package com.example.androidapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.adapters.CatsAdapter
import com.example.androidapplication.databaseManagement.DBHelper
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.iterator.Aggregate
import com.example.androidapplication.iterator.Collection

class CollectionActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var cat_view: RecyclerView
    private lateinit var aggregate: Collection
    private lateinit var inOrderButton: Button
    private lateinit var backBtn: Button
    private lateinit var toMainWindow: Button
    private lateinit var userCollection: ArrayList<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        dbHelper = DBHelper(this)

        // Assigning all views
        toMainWindow = findViewById(R.id.ToMainWindow)
        inOrderButton = findViewById(R.id.InOrderTraversal)
        backBtn = findViewById(R.id.BackwardsTraversal)
        cat_view = findViewById(R.id.cat_view)

        // Getting user information from the previous activity
        val user = intent.getIntExtra("user_ID", 0)
        val isSpy = intent.getIntExtra("isSpy", 0)

        // Creating the aggregate class and assign the user collection
        userCollection = dbHelper.getListOfCatsByUser(user)
        aggregate = Aggregate(userCollection)

        cat_view.layoutManager = LinearLayoutManager(this)
        cat_view.adapter = CatsAdapter(this, userCollection)

        // Setting click listeners
        inOrderButton.setOnClickListener {
            traverInOrder()
        }
        backBtn.setOnClickListener {
            backwardsTraversal()
        }
        toMainWindow.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_ID", user)
            intent.putExtra("isSpy", isSpy)
            startActivity(intent)
        }
    }

    private fun traverseInOrderCollection(): ArrayList<Animal> {
        // Creating in order iterator
        val inOrderCollection = ArrayList<Animal>()
        val iterator = aggregate.createInOrderIterator()
        while (iterator.hasNext()) {
            inOrderCollection.add(iterator.next())
        }
        return inOrderCollection
    }

    private fun traverseInBackwardsCollection(): ArrayList<Animal> {
        // Creating backwards iterator
        val randomCollection = ArrayList<Animal>()
        val iterator = aggregate.createBackwardsIterator()
        while (iterator.hasNext()) {
            randomCollection.add(iterator.next())
        }
        return randomCollection
    }

    private fun traverInOrder() {
        cat_view.adapter = CatsAdapter(this, traverseInOrderCollection())
    }

    private fun backwardsTraversal() {
        cat_view.adapter = CatsAdapter(this, traverseInBackwardsCollection())
    }
}