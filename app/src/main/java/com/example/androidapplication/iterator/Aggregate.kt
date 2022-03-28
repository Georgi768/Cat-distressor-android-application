package com.example.androidapplication.iterator

import com.example.androidapplication.factory.Animal

class Aggregate(collection : ArrayList<Animal>) : Collection {
    private val animalCollection = collection

    override fun createBackwardsIterator(): Iterator {
        return TraversBackwards(animalCollection)
    }

    override fun createInOrderIterator(): Iterator {
        return TraverseInOrder(animalCollection)
    }
}