package com.example.androidapplication.iterator

import com.example.androidapplication.factory.Animal

class TraversBackwards(userCollection : ArrayList<Animal>) : Iterator {
    protected val collection = createBackwardsCollection(userCollection)
    protected var position = 0
    override fun hasNext(): Boolean {
        if(position >= collection.size)
        {
            return false
        }
        return true
    }

    override fun next(): Animal {
        val animal = collection.get(position)
        position++
        return animal
    }

    private fun createBackwardsCollection(collectionAnimals : ArrayList<Animal>) : ArrayList<Animal>
    {
        val backwardsCollection = ArrayList<Animal>()
        for (i in collectionAnimals.size downTo 1)
        {
            val animal = collectionAnimals.get(i -1)
            backwardsCollection.add(animal)
        }
        return backwardsCollection
    }
}