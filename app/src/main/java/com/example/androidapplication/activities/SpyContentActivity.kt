package com.example.androidapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapplication.R
import com.example.androidapplication.adapters.SpyAdapter
import com.example.androidapplication.commands.GetNext
import com.example.androidapplication.commands.ICommand
import com.example.androidapplication.commands.Save
import com.example.androidapplication.databaseManagement.DBHelper
import com.example.androidapplication.factory.Animal
import com.example.androidapplication.iterator.Aggregate
import com.example.androidapplication.iterator.Collection
import com.google.android.material.textfield.TextInputEditText

class SpyContentActivity : AppCompatActivity(), Window {
    private var dbHelper: DBHelper = DBHelper(this)
    private lateinit var userCollection: RecyclerView
    private lateinit var userInput: TextInputEditText
    private lateinit var nextCommand: ICommand
    private lateinit var saveCommand: ICommand
    private lateinit var homePageBtn: Button
    private lateinit var mCats: ArrayList<Animal>
    private lateinit var aggregate: Collection
    private var id: Int = 0
    private var spyValidation: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spyusercollection)

        // Assigning all views
        userCollection = findViewById(R.id.userCollection)
        userInput = findViewById(R.id.userSearch)
        homePageBtn = findViewById(R.id.backToMainPage)
        mCats = ArrayList()

        // Setting up the commands
        nextCommand = GetNext(this)
        saveCommand = Save(this)

        userCollection.layoutManager = LinearLayoutManager(this)

        // Getting user data
        val data = intent
        id = data.getIntExtra("user_ID", 0)
        spyValidation = data.getIntExtra("isSpy", 0)

        /*
        Save command works with the adapter for performing the next command
        and providing the functionality to select and item from a collection and save an item.
         */
        saveCommand.execute()
        homePageBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user_ID", id)
            intent.putExtra("isSpy", spyValidation)
            startActivity(intent)
        }
    }

    private fun generateInOrderTraversalMethod(): ArrayList<Animal> {
        // Method for the iterator, used to iterate throughout the collection in order
        val iteratedCollection = ArrayList<Animal>()
        val iterator = aggregate.createInOrderIterator()
        while (iterator.hasNext()) {
            iteratedCollection.add(iterator.next())
        }
        return iteratedCollection
    }

    private fun searchUser(): ArrayList<Animal> {
        println(userInput.text)
        return dbHelper.getUserCollection(userInput.text.toString())
    }

    override fun next() {
        /*
        populating one item after another in the spy collection
        and performing iterator traversal
         */
        mCats = searchUser()
        aggregate = Aggregate(mCats)
        userCollection.adapter = SpyAdapter(id, this, generateInOrderTraversalMethod())
    }

    override fun save() {
        userInput.doAfterTextChanged {
            nextCommand.execute()
        }
    }
}

