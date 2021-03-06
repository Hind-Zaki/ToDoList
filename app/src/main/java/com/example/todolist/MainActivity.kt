package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.Todo.toDoListFragment.ToDoListListFragment
import com.example.todolist.ToDoListFragment.ToDoListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = ToDoListListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }


        }
}