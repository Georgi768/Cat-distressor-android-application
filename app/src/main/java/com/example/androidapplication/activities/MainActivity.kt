package com.example.androidapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.androidapplication.R
import com.example.androidapplication.user.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    //Ui variables
    private lateinit var getNewCatButton: FloatingActionButton
    private lateinit var getCatInfoButton: FloatingActionButton
    private lateinit var catImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNewCatButton = findViewById(R.id.getNewCatButton)
        getCatInfoButton = findViewById(R.id.getCatInfoButton)
        catImageView = findViewById(R.id.catImage)
    }
}