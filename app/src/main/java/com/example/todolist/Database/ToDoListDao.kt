package com.example.todolist.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface ToDoDao{

    @Query("SELECT*FROM toDoList")
    fun getToDos():LiveData<List<ToDoList>>


    @Query( "SELECT* FROM toDoList WHERE id=(:id)")
    fun getToDo(id: UUID):LiveData<ToDoList?>


    @Update
    fun updateToDo(toDoList: ToDoList)



    @Insert
    fun addToDo(toDoList: ToDoList)


    @Delete
    fun deleteToDo(toDoList: ToDoList)









}



