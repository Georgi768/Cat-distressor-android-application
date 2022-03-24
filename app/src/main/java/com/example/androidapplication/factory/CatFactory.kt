package com.example.androidapplication.factory

class CatFactory() : Factory {

    override fun CreateAnimal(
        id: Int,
        breed: String,
        description: String,
        imageURL: String
    ): Animal {

        return Animal(id, breed, description, imageURL)
    }

    companion object CreateAnimal{
        fun CreateAnimal(
            id: Int,
            breed: String,
            description: String,
            imageURL: String
        ): Animal = CreateAnimal(id, breed, description, imageURL)
    }
}