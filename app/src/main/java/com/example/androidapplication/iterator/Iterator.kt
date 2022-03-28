package com.example.androidapplication.iterator

import com.example.androidapplication.factory.Animal

interface Iterator {
     fun hasNext():Boolean
     fun next(): Animal
}