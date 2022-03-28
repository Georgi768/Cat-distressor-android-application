package com.example.androidapplication.iterator

interface Collection {
    fun createBackwardsIterator() : Iterator
    fun createInOrderIterator() : Iterator
}