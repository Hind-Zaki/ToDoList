package com.example.todolist.ToDoListListFragment

import androidx.lifecycle.ViewModel
import com.example.todolist.Database.ToDoList
import com.example.todolist.Database.ToDoListRepository

class ToDoListViewModel : ViewModel() {

    private val toDoListRepository=ToDoListRepository.get()
    val toDoListLiveData=toDoListRepository.getAllToDoLists()

    fun addToDo(toDoList: ToDoList) {
        toDoListRepository.addToDoList(toDoList)
    }
    fun deleteToDo(toDoList: ToDoList){
        toDoListRepository.deleteToDoList(toDoList)
    }
}