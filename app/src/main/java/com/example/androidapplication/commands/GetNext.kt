package com.example.androidapplication.commands

import com.example.androidapplication.activities.Window

class GetNext(private val window :Window) : ICommand{
    override fun execute() {
        window.next()
    }
}