package com.example.todolist.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ToDoList::class],version = 1,exportSchema = false)
@TypeConverters(ToDoListTypeConverters::class)
abstract class ToDoListDatabase:RoomDatabase()  {

    abstract fun toDoListDao():ToDoDao


}