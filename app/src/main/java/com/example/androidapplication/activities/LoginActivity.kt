package com.example.androidapplication.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapplication.R
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signUpText: TextView
    private lateinit var btnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnSignIn = findViewById(R.id.buttonSignIn)
        signUpText = findViewById(R.id.signUpText)
    }


}