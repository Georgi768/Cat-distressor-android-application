package com.example.androidapplication.factory

class CatFactory() : Factory {
    override fun createAnimal(id: Int, breed: String?, description: String?, imageURL: String): Animal
    {
        return Cat(id, breed, imageURL)
    }
}