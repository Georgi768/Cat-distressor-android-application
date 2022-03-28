package com.example.androidapplication.iterator

import com.example.androidapplication.factory.Animal
import java.util.*
import kotlin.collections.ArrayList

class TraverseRandom(collectionAnimals: ArrayList<Animal>) : Iterator{
    private val randomCollection : ArrayList<Animal> = collectionAnimals
    private var position = 0
    override fun hasNext(): Boolean {
        if(position >= randomCollection.size)
        {
            return false
        }
        return true
    }

    override fun next(): Animal {
        val animal = randomCollection.get(position)
        position++
        return animal
    }
    private fun createArrayListWithRandomOrderItems(collection : ArrayList<Animal>) : ArrayList<Animal>
    {
        val randomCollection : ArrayList<Animal> = collection

        randomCollection.shuffle()
        return randomCollection
    }
}