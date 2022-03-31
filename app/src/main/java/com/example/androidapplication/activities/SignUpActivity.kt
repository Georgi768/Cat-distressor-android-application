package com.example.androidapplication.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapplication.R
import com.example.androidapplication.databaseManagement.DBHelper
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private lateinit var database : DBHelper
    private lateinit var spyButton : Button
    var spyValidation : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val username = findViewById<TextInputEditText>(R.id.username)
        val password = findViewById<TextInputEditText>(R.id.password)
        val btnSignUp = findViewById<Button>(R.id.buttonSignUp)
        val loginText = findViewById<TextView>(R.id.logintext)
        spyButton = findViewById(R.id.spy_activation)

        database =  DBHelper(this)

        btnSignUp.setOnClickListener{
            insertData(username.text.toString(),password.text.toString(),database)
        }

        loginText.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        spyButton.setOnClickListener {
            Toast.makeText(this,"You are a spy Harry!",Toast.LENGTH_LONG).show()
            spyValidation = 1
        }
    }
    private fun insertData(name:String,pass:String,database:DBHelper)
    {
        if(name.isNotBlank() && pass.isNotBlank())
        {
            if(!userExist(name))
            {
                if(database.insertIntoDatabase(name,pass,spyValidation))
                    Toast.makeText(this,"Entry added",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
            }else
            {
                Toast.makeText(this,"Username already exist",Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this,"Entry is empty",Toast.LENGTH_SHORT).show()
        }
    }

    private fun userExist(username: String) : Boolean
    {
        val query = "SELECT * FROM User WHERE Name = ?"

        val string = arrayOf(username)
        val cursor = database.readableDatabase.rawQuery(query,string)
        val count :Int = cursor.count
        cursor.close()
        return count >= 1
    }

}