package com.example.androidapplication.commands

import com.example.androidapplication.activities.Window

class GetNext(windows :Window) : ICommand{
    private val window = windows
    override fun execute() {
        window.next()
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}