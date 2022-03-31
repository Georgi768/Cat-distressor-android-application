package com.example.androidapplication.factory

interface Factory {

    fun createAnimal(id: Int, breed: String?, description: String?, imageURL: String): Animal
}