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
import com.example.androidapplication.factory.Cat
import com.google.android.material.textfield.TextInputEditText

class SpyContentActivity : AppCompatActivity(), Window {
    private var dbHelper: DBHelper = DBHelper(this)
    private lateinit var userCollection : RecyclerView
    private lateinit var userInput : TextInputEditText
    private lateinit var saveCommand : ICommand
    private lateinit var mCats : ArrayList<Animal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spyusercollection)
        userCollection = findViewById(R.id.userCollection)
        userInput = findViewById(R.id.userSearch)
        saveCommand = Save(this)

        mCats = ArrayList()
        userCollection.layoutManager = LinearLayoutManager(this)


        userInput.doAfterTextChanged{
            mCats = searchUser()
        }
        userCollection.adapter = SpyAdapter(this,generateList(mCats))
    }


    private fun generateList(listOfUserAnimals : ArrayList<Animal>) : ArrayList<Animal>
    {
        val list = ArrayList<Animal>()
        listOfUserAnimals.forEach {
            list.add(Cat(it.id, it.breed, it.description, it.imageURL))
        }
        return list
    }

    override fun next() {
        TODO("Not yet implemented")
    }

    override fun performAction() {
    }

    private fun searchUser(): ArrayList<Animal> {
        println(userInput.text)
        return dbHelper.getUserCollection(userInput.text.toString())
    }
}

