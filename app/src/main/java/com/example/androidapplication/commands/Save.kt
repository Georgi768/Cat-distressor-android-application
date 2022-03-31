package com.example.androidapplication.commands

import com.example.androidapplication.activities.Window

class Save(window : Window) : ICommand {
    private val window = window
    override fun execute() {
        window.performAction()
    }

    override fun undo() {
        TODO("Not yet implemented")
    }
}