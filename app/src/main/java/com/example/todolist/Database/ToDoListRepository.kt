package com.example.todolist.Database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "toDoList-database"

class ToDoListRepository private constructor(context: Context) {

    private val database: ToDoListDatabase = Room.databaseBuilder(
        context.applicationContext,
        ToDoListDatabase::class.java,
        DATABASE_NAME
    ).build()


    private val toDoListDao = database.toDoListDao()


    private val executor = Executors.newSingleThreadExecutor()


    fun getAllToDoLists(): LiveData<List<ToDoList>> = toDoListDao.getToDos()
    fun getToDoList(id: UUID): LiveData<ToDoList?> = toDoListDao.getToDo(id)


    fun addToDoList(toDoList: ToDoList) {
        executor.execute {
            toDoListDao.addToDo(toDoList)
        }
    }

    fun deleteToDoList(toDoList: ToDoList) {
        executor.execute {
            toDoListDao.deleteToDo(toDoList)
        }

    }

    fun updateToDoList(toDoList: ToDoList) {
        executor.execute {
            toDoListDao.updateToDo(toDoList)
        }
    }

    companion object {
        private var INSTANCE: ToDoListRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ToDoListRepository(context)

            }
        }

        fun get(): ToDoListRepository {
            return INSTANCE
                ?: throw IllegalStateException("ToDoRepository must be initialized")
        }
    }
}



