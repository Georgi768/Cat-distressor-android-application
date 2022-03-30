package com.example.androidapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
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

class MainActivity : AppCompatActivity(), Window {
    private var dbHelper: DBHelper = DBHelper(this)
    private lateinit var saveAnimal: FloatingActionButton
    private lateinit var getCatInfoButton: FloatingActionButton
    private lateinit var catImageView: ImageView
    private lateinit var nextCommand: ICommand
    private lateinit var spyCollectionBtn : Button
    private var requestQueue: RequestQueue? = null
    private var currentCatName :String? = null
    private lateinit var currentCatUrl: String
    private var currentCatDescription: String? = null
    private lateinit var newCatBtn : Button
    private lateinit var themeSwitch : SwitchCompat
    private lateinit var saveCatCommand : ICommand
    private var userID : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCatImage(resources.getString(R.string.api_url))
        val data = intent
        spyCollectionBtn = findViewById(R.id.SpyCollection)
        themeSwitch = findViewById(R.id.ThemeSwitch)
        userID = data.getIntExtra("user_ID",0)
        isUserSpy(data.getIntExtra("isSpy",0))
        nextCommand = GetNext(this)
        saveCatCommand = Save(this)
        saveAnimal = findViewById(R.id.SaveToCollection)
        getCatInfoButton = findViewById(R.id.getCatInfoButton)
        catImageView = findViewById(R.id.catImage)
        newCatBtn = findViewById(R.id.getNewCatBtn)

        currentCatUrl = ""
        currentCatDescription = null
        currentCatName = null


        getCatInfoButton.setOnClickListener {
            val intent = Intent( this, CollectionActivity::class.java)
            intent.putExtra("user_ID", userID)
            startActivity(intent)
        }
        saveAnimal.setOnClickListener {
            saveCatCommand.execute()
        }
        newCatBtn.setOnClickListener {
            nextCommand.execute()
        }

        spyCollectionBtn.setOnClickListener {
            startActivity(Intent(this, SpyContentActivity::class.java))
        }

        //ewCatBtn.isVisible = false
    }


    override fun next() {
        getCatImage(resources.getString(R.string.api_url))
    }

    private fun isUserSpy(spyNumber : Int) : Boolean
    {
        if(spyNumber == 1){
            spyCollectionBtn.isVisible = true
            themeSwitch.isVisible = true
            println("this is a sply user")
            return true
        }
        spyCollectionBtn.isVisible = false
        themeSwitch.isVisible = false
        return false
    }

    private fun getCatImage(url: String) {
        requestQueue = Volley.newRequestQueue(this)
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val catData = response.getJSONObject(0)
                currentCatUrl = catData.getString("url")
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

    override fun performAction() {
        //Save in the database for cats, and saved to userCollection
        val catURl = currentCatUrl
        dbHelper.insertCatIntoDatabase(currentCatName, currentCatDescription, catURl)
        println("CatInserted")
        dbHelper.addCatInUserCollection(userID,currentCatUrl)

        println("Cat saved$currentCatName he $currentCatDescription$catURl")
    }
}