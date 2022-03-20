package com.example.androidapplication.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapplication.R
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val email = findViewById<TextInputEditText>(R.id.email)
        val username = findViewById<TextInputEditText>(R.id.username)
        val password = findViewById<TextInputEditText>(R.id.password)
        val btnSignUp = findViewById<Button>(R.id.buttonSignUp)
        val loginText = findViewById<TextView>(R.id.logintext)
    }
}