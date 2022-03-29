package com.example.androidapplication.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.adapters.CatsAdapter
import com.example.androidapplication.commands.ICommand
import com.example.androidapplication.commands.Save
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.factory.Cat

class SpyContentActivity : AppCompatActivity(), Window {
    private lateinit var userCollection : RecyclerView
    private lateinit var stealBtn : Button
    private lateinit var userInput : TextView
    private lateinit var saveCommand : ICommand

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spyusercollection)
        stealBtn = findViewById(R.id.Steal)
        userCollection = findViewById(R.id.userCollection)
        userInput = findViewById(R.id.userSearch)
        saveCommand = Save(this)

        userCollection.layoutManager = LinearLayoutManager(this)

        userCollection.adapter = CatsAdapter(this,generateList())

        stealBtn.setOnClickListener {

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
        TODO("Not yet implemented")
    }

    private fun getImage()
    {
        if(userInput.text.toString().isNotBlank())
        {

        }
    }

}

