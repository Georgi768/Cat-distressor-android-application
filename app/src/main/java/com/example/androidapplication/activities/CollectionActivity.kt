package com.example.androidapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.adapters.CatsAdapter
import com.example.androidapplication.commands.GetLast
import com.example.androidapplication.commands.GetNext
import com.example.androidapplication.commands.ICommand
import com.example.androidapplication.databaseManagement.DBHelper
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.factory.Cat
import com.example.androidapplication.iterator.Aggregate
import com.example.androidapplication.iterator.Collection
import com.example.androidapplication.iterator.TraverseInOrder

class CollectionActivity : AppCompatActivity(),Window {
    private lateinit var cat_view : RecyclerView
    private lateinit var aggregate : Collection
    private lateinit var inOrderButton : Button
    private lateinit var backBtn : Button
    private lateinit var toMainWindow : Button
    private lateinit var nextCommand : ICommand
    private lateinit var lastCommand : ICommand
    private var db : DBHelper = DBHelper(this);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        nextCommand = GetNext(this)
        lastCommand = GetLast(this)

        toMainWindow = findViewById(R.id.ToMainWindow)
        inOrderButton = findViewById(R.id.InOrderTraversal)
        backBtn = findViewById(R.id.BackwardsTraversal)
        cat_view = findViewById(R.id.cat_view)

        var userID = intent.getIntExtra("user_ID", 0)
        aggregate = Aggregate(db.getListOfCatsByUser(userID))

        cat_view.layoutManager = LinearLayoutManager(this)

        cat_view.adapter = CatsAdapter(this,traverseInOrderCollection())


        inOrderButton.setOnClickListener{
            nextCommand.execute()
        }

        backBtn.setOnClickListener{
            lastCommand.execute()
        }

        toMainWindow.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
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

    override fun next() {
        cat_view.adapter = CatsAdapter(this,traverseInOrderCollection())
    }

    override fun performAction() {
        cat_view.adapter = CatsAdapter(this,traverseCollectionInBackwards())
    }
}