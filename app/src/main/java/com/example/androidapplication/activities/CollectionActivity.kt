package com.example.androidapplication.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapplication.R
import com.example.androidapplication.adapters.CatsAdapter
import com.example.androidapplication.models.Cat


class CollectionActivity : AppCompatActivity() {
    private lateinit var cats: ArrayList<Cat>
    private lateinit var mCatsAdapter: CatsAdapter
    private lateinit var catImageView: ImageView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        button.setOnClickListener {
            mCatsAdapter.addCat(Cat("AAA", "aSHSAOID"))
            mCatsAdapter.notifyDataSetChanged()
        }
    }
}