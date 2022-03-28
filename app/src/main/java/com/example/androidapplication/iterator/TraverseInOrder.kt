package com.example.androidapplication.iterator

import com.example.androidapplication.factory.Animal

class TraverseInOrder(userCollection : ArrayList<Animal>) : Iterator{
    protected val collection = userCollection
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
}