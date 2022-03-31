package com.example.androidapplication.commands

import com.example.androidapplication.activities.Window

class Save(private val window : Window) : ICommand {
    override fun execute() {
        window.save()
    }
}