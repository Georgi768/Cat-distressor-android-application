package com.example.androidapplication.iterator

import com.example.androidapplication.factory.Animal

class TraversBackwards(userCollection : ArrayList<Animal>) : Iterator {
    private val collection = createBackwardsCollection(userCollection)
    private var position = 0
    override fun hasNext(): Boolean {
        if(position >= collection.size)
        {
            return false
        }
        return true
    }

    override fun next(): Animal {
        val animal = collection[position]
        position++
        return animal
    }

    private fun createBackwardsCollection(collectionAnimals : ArrayList<Animal>) : ArrayList<Animal>
    {
        val backwardsCollection = ArrayList<Animal>()
        for (i in collectionAnimals.size downTo 1)
        {
            val animal = collectionAnimals[i -1]
            backwardsCollection.add(animal)
        }
        return backwardsCollection
    }
}