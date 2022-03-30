package com.example.androidapplication.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.adapters.SpyAdapter
import com.example.androidapplication.commands.ICommand
import com.example.androidapplication.commands.Save
import com.example.androidapplication.databaseManagement.DBHelper
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.iterator.Aggregate
import com.example.androidapplication.iterator.Collection
import com.google.android.material.textfield.TextInputEditText

class SpyContentActivity : AppCompatActivity(),Window {
    private var dbHelper: DBHelper = DBHelper(this)
    private lateinit var userCollection : RecyclerView
    private lateinit var userInput : TextInputEditText
    private lateinit var saveCommand : ICommand
    private lateinit var mCats : ArrayList<Animal>
    private lateinit var aggregate : Collection
    private var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        saveCommand = Save(this)
        setContentView(R.layout.activity_spyusercollection)
        userCollection = findViewById(R.id.userCollection)
        userInput = findViewById(R.id.userSearch)

        mCats = ArrayList()
        userCollection.layoutManager = LinearLayoutManager(this)

        val data = intent
        id = data.getIntExtra("user_ID",0)
        userInput.doAfterTextChanged{
            saveCommand.execute()
        }
    }
    private fun generateList() : ArrayList<Animal>
    {
        val iteratedCollection = ArrayList<Animal>()
        val iterator = aggregate.createInOrderIterator()
        while (iterator.hasNext()){
            iteratedCollection.add(iterator.next())
        }
        return iteratedCollection
    }

    private fun searchUser(): ArrayList<Animal> {
        println(userInput.text)
        return dbHelper.getUserCollection(userInput.text.toString())
    }

    override fun next() {
        TODO("Not yet implemented")
    }

    override fun performAction() {
        mCats = searchUser()
        aggregate = Aggregate(mCats)
        userCollection.adapter = SpyAdapter(id,this,generateList())
    }
}

