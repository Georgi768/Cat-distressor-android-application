package com.example.androidapplication.user

import com.example.androidapplication.factory.Animal

abstract class User(name:String, collection: ArrayList<Animal>) {
    private var animalCollection = collection

}