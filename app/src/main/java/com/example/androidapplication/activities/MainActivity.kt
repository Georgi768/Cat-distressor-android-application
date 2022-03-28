package com.example.androidapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.androidapplication.R
import com.example.androidapplication.commands.GetNext
import com.example.androidapplication.commands.ICommand
import com.example.androidapplication.databaseManagement.DBHelper
import com.example.androidapplication.user.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException

class MainActivity : AppCompatActivity(), Window{
    private var dbHelper: DBHelper = DBHelper(this)
    private lateinit var getNewCatButton: FloatingActionButton
    private lateinit var getCatInfoButton: FloatingActionButton
    private lateinit var catImageView: ImageView
    private lateinit var nextCommand : ICommand
    private var requestQueue: RequestQueue? = null
    private var currentCatName :String? = null
    private lateinit var currentCatUrl: String
    private var currentCatDescription: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nextCommand = GetNext(this)
        getNewCatButton = findViewById(R.id.getNewCatButton)
        getCatInfoButton = findViewById(R.id.getCatInfoButton)
        catImageView = findViewById(R.id.catImage)

        currentCatDescription = null
        currentCatName = null
        currentCatUrl = ""
        getCatInfoButton.setOnClickListener {
            saveCat(currentCatUrl)
        }

        nextCommand.execute()
    }


    override fun next() {
        println("My ass")
        getCatImage(resources.getString(R.string.api_url))
        getNewCatButton.setOnClickListener {
            getCatImage(resources.getString(R.string.api_url))
        }
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
    private fun saveCat(catUrl: String){
        val catUrl = currentCatUrl
        dbHelper.insertCatIntoDatabase(currentCatName, currentCatDescription, catUrl)
        println("Cat saved$currentCatName he $currentCatDescription$currentCatUrl")
    }
}