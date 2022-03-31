package com.example.androidapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.androidapplication.R
import com.example.androidapplication.commands.GetNext
import com.example.androidapplication.commands.ICommand
import com.example.androidapplication.commands.Save
import com.example.androidapplication.databaseManagement.DBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), Window {
    private var dbHelper: DBHelper = DBHelper(this)
    private lateinit var saveAnimal: FloatingActionButton
    private lateinit var getCatInfoButton: FloatingActionButton
    private lateinit var catImageView: ImageView
    private lateinit var nextCommand: ICommand
    private lateinit var spyCollectionBtn: Button
    private var requestQueue: RequestQueue? = null
    private var currentCatName: String? = null
    private lateinit var currentCatUrl: String
    private var currentCatDescription: String? = null
    private lateinit var newCatBtn: Button
    private lateinit var themeSwitch: SwitchCompat
    private lateinit var saveCatCommand: ICommand
    private var userID: Int = 0
    private var spyNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assigning all views
        spyCollectionBtn = findViewById(R.id.SpyCollection)
        userID = data.getIntExtra("user_ID",0)
        currentUserId = userID
        spyNumber = data.getIntExtra("isSpy",0)
        isUserSpy(spyNumber)
        nextCommand = GetNext(this)
        saveCatCommand = Save(this)
        saveAnimal = findViewById(R.id.SaveToCollection)
        getCatInfoButton = findViewById(R.id.getCatInfoButton)
        catImageView = findViewById(R.id.catImage)
        newCatBtn = findViewById(R.id.getNewCatBtn)

        getCatImage(resources.getString(R.string.api_url))
        val data = intent

        // Check user ID and Spy validation
        userID = data.getIntExtra("user_ID", 0)
        spyNumber = data.getIntExtra("isSpy", 0)
        isUserSpy(spyNumber)

        //Command pattern for next and save
        nextCommand = GetNext(this)
        saveCatCommand = Save(this)

        // Instantiating variables for the API
        currentCatUrl = ""
        currentCatDescription = null
        currentCatName = null

        // Set click listeners
        getCatInfoButton.setOnClickListener {
            val intent = Intent(this, CollectionActivity::class.java)
            intent.putExtra("user_ID", userID)
            intent.putExtra("isSpy", spyNumber)
            startActivity(intent)
        }
        saveAnimal.setOnClickListener {
            saveCatCommand.execute()
        }
        newCatBtn.setOnClickListener {
            nextCommand.execute()
        }

        spyCollectionBtn.setOnClickListener {
            val intent = Intent(this, SpyContentActivity::class.java)
            intent.putExtra("user_ID", userID)
            intent.putExtra("isSpy", spyNumber)
            startActivity(intent)
        }
    }

    override fun next() {
        getCatImage(resources.getString(R.string.api_url))
    }

    private fun isUserSpy(spyNumber: Int): Boolean {
        if (spyNumber == 1) {
            spyCollectionBtn.isVisible = true
            println("this is a sply user")
            return true
        }
        spyCollectionBtn.isVisible = false
        return false
    }

    private fun getCatImage(url: String) {
        requestQueue = Volley.newRequestQueue(this)
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val catData = response.getJSONObject(0)
                currentCatUrl = catData.getString("url")
                // button to get ifo
                val breedInfo = catData.getJSONArray("breeds")
                if (breedInfo.isNull(0)) {
                    println("No data available")
                } else {
                    val breedData = breedInfo.getJSONObject(0)
                    currentCatDescription = breedData.getString("description")
                    currentCatName = breedData.getString("name")
                }
                Glide.with(this)
                    .load(currentCatUrl)
                    .override(
                        200,
                        600
                    ) // resizes the image to 100x200 pixels but does not respect aspect ratio
                    .into(catImageView)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    override fun save() {
        //Save in the database for cats, and saved to userCollection
        val catURl = currentCatUrl
        dbHelper.insertCatIntoDatabase(currentCatName, currentCatDescription, catURl)
        println("CatInserted")
        dbHelper.addCatInUserCollection(userID, currentCatUrl)
    }
}