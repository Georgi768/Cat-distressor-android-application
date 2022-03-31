package com.example.androidapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapplication.R
import com.example.androidapplication.databaseManagement.DBHelper
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var database: DBHelper
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signUpText: TextView
    private lateinit var btnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = DBHelper(this)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnSignIn = findViewById(R.id.buttonSignIn)
        signUpText = findViewById(R.id.signUpText)

        btnSignIn.setOnClickListener {
           logInValidation(username.text.toString(),password.text.toString())
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun logInValidation(user : String,pass: String)
    {
        if (user.isNotEmpty() && pass.isNotEmpty()) {
            val login = database.loginUser(user, pass)
            if (login) {
                val query = "SELECT ID as user_ID, SpyUser as isSpy FROM User WHERE Name = ?"
                val queryCursor = database.readableDatabase.rawQuery(
                    query,
                    arrayOf(user)
                )
                queryExecution(queryCursor)
            } else {
                Toast.makeText(
                    this,
                    "Sign in unsuccessful, edit password or username",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(this, "All required", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("Range")
    private fun queryExecution(queryCursor : Cursor)
    {
        queryCursor.moveToFirst()
        val iD = queryCursor.getInt(queryCursor.getColumnIndex("user_ID"))
        queryCursor.moveToLast()
        val isSpy = queryCursor.getInt(queryCursor.getColumnIndex("isSpy"))
        queryCursor.close()

        Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user_ID", iD)
        intent.putExtra("isSpy", isSpy)
        startActivity(intent)
    }
}