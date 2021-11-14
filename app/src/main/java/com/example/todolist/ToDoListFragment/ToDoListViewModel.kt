package com.example.todolist.ToDoListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.Database.ToDoList
import com.example.todolist.Database.ToDoListRepository
import java.util.*


class ToDoViewModel : ViewModel() {
    private val toDoListRepository = ToDoListRepository.get()
    private val toDoListIdLiveData = MutableLiveData<UUID>()

    var toDoListLiveData: LiveData<ToDoList?> =
        Transformations.switchMap(toDoListIdLiveData) {
            toDoListRepository.getToDoList(it)
        }

    fun loadToDoList(toDoListId: UUID) {
        toDoListIdLiveData.value = toDoListId
    }


    fun saveUpdate(toDoList: ToDoList) {
        toDoListRepository.updateToDoList(toDoList)
    }


    fun addToDo(toDoList: ToDoList) {
        toDoListRepository.addToDoList(toDoList)
    }

}