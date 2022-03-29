package com.example.androidapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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
import com.example.androidapplication.factory.Animal
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException

class MainActivity : AppCompatActivity(), Window{
    private var dbHelper: DBHelper = DBHelper(this)
    private lateinit var saveAnimal: FloatingActionButton
    private lateinit var getCatInfoButton: FloatingActionButton
    private lateinit var catImageView: ImageView
    private lateinit var nextCommand : ICommand
    private var requestQueue: RequestQueue? = null
    private var currentCatName :String? = null
    private var currentCatUrl: String? = null
    private var currentCatDescription: String? = null
    private lateinit var newCatBtn : Button
    private lateinit var saveCatCommand : ICommand

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextCommand = GetNext(this)
        saveCatCommand = Save(this)
        saveAnimal = findViewById(R.id.SaveToCollection)
        getCatInfoButton = findViewById(R.id.getCatInfoButton)
        catImageView = findViewById(R.id.catImage)
        newCatBtn = findViewById(R.id.getNewCatBtn)

        currentCatDescription = null
        currentCatName = null

        getCatInfoButton.setOnClickListener {
            startActivity(Intent(this,CollectionActivity::class.java))
        }
        saveAnimal.setOnClickListener{
            saveCatCommand.execute()
        }
        newCatBtn.setOnClickListener{
            nextCommand.execute()
        }
    }

    override fun next() {
        getCatImage(resources.getString(R.string.api_url))
    }

    private fun getCatImage(url: String) {
        requestQueue = Volley.newRequestQueue(this)
        val request = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val catData = response.getJSONObject(0)
                currentCatUrl = catData.getString("url")
                Glide.with(this)
                    .load(currentCatUrl)
                    .override(200, 600) // resizes the image to 100x200 pixels but does not respect aspect ratio
                    .into(catImageView)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }
    override fun performAction() {
        //Save in the database for cats, and saved to userCollection
        val emptyString = ""
        dbHelper.insertCatIntoDatabase(currentCatName, currentCatDescription, emptyString)
        println("Cat saved$currentCatName he $currentCatDescription$emptyString")
    }
}