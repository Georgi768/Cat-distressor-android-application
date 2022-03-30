package com.example.androidapplication.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapplication.R
import com.example.androidapplication.databaseManagement.DBHelper
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private lateinit var database : DBHelper
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signUpText: TextView
    private lateinit var btnSignIn: Button

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        database = DBHelper(this)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        btnSignIn = findViewById(R.id.buttonSignIn)
        signUpText = findViewById(R.id.signUpText)

        btnSignIn.setOnClickListener {
            val usernameToPass= username.text
            val passwordToPass = password.text

            if (!usernameToPass.isNullOrEmpty() && !passwordToPass.isNullOrEmpty()){
                val login = database.loginUser(usernameToPass.toString(), passwordToPass.toString())
                if (login) {
                    val query = "SELECT ID as user_ID FROM User WHERE Name = ?"
                    val userID = database.readableDatabase.rawQuery(query, arrayOf(usernameToPass.toString()))
                    userID.moveToFirst()
                    val iD = userID.getInt(userID.getColumnIndex("user_ID"))
                    userID.close()

                    Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent( this, MainActivity::class.java)
                    intent.putExtra("user_ID", iD)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Sign in unsuccessful, edit password or username", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All required", Toast.LENGTH_SHORT).show()
            }
        }

        signUpText.setOnClickListener {
            startActivity(Intent( this, SignUpActivity::class.java))
        }
    }


}