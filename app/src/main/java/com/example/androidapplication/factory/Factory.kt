package com.example.androidapplication.factory

interface Factory {

    fun CreateAnimal(id: Int, breed: String?, description: String?, imageURL: String): Animal
}