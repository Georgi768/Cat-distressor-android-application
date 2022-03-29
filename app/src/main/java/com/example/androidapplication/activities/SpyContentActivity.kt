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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spyusercollection)
        userCollection = findViewById(R.id.userCollection)
        userInput = findViewById(R.id.userSearch)
        saveCommand = Save(this)

        userCollection.layoutManager = LinearLayoutManager(this)

        userCollection.adapter = SpyAdapter(this,generateList())
        userInput.doAfterTextChanged{
            performAction()
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

    override fun next() {
        TODO("Not yet implemented")
    }

    override fun performAction() {

    }

    private fun getImage()
    {
        if(userInput.text.toString().isNotBlank())
        {

        }
    }

}

