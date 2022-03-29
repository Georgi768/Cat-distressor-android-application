package com.example.androidapplication.commands

import com.example.androidapplication.activities.Window

class GetLast(window : Window) : ICommand {
    private val window = window
    override fun execute() {
        window.performAction()
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}