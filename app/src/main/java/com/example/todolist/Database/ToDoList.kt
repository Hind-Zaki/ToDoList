package com.example.todolist.Database

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ToDoList(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var details: String = "",
    var creationDate: Date = Date(),
    var dueDate: Date? = null,
    var isCompleted: Boolean = false,


)
