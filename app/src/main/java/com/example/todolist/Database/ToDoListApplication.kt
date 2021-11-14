package com.example.todolist.Database

import android.app.Application

class ToDoListApplication:Application() {

        override fun onCreate() {
            super.onCreate()
            ToDoListRepository.initialize(this)
        }
    }
