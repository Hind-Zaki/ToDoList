package com.example.Todo.toDoListFragment

import android.os.Bundle
import android.text.format.DateFormat

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Database.ToDoList
import com.example.todolist.R
import com.example.todolist.ToDoListFragment.ToDoListFragment
import com.example.todolist.ToDoListListFragment.ToDoListViewModel
import java.util.*

const val KEY_ID = "toDoListTd"


class ToDoListListFragment : Fragment() {


    private lateinit var toDoRecyclerView: RecyclerView


    private val toDoListViewModel
            by lazy {
                ViewModelProvider(this)
                    .get(ToDoListViewModel::class.java)
            }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_button -> {
                val toDoList = ToDoList()
                toDoListViewModel.addToDo(toDoList)

                val args = Bundle()
                args.putSerializable(KEY_ID, toDoList.id)
                val fragment = ToDoListFragment()
                fragment.arguments = args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_to_do_list_list, container, false)
        toDoRecyclerView = view.findViewById(R.id.toDoList_recycler_view)
        val linearLayoutManager = LinearLayoutManager(context)
        toDoRecyclerView.layoutManager = linearLayoutManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoListViewModel.toDoListLiveData.observe(
            viewLifecycleOwner,
            Observer { toDoLists ->
                toDoLists?.let {
                    updatedUI(it)
                }
            }
        )


    }

    private fun updatedUI(toDoLists: List<ToDoList>) {
        val toDoAdapter = ToDoAdapter(toDoLists)
        toDoRecyclerView.adapter = toDoAdapter
    }

    private inner class ToDoHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var toDoList: ToDoList
        private val titleTextView: TextView = itemView.findViewById(R.id.toDo_title_item)
        private val dateTextView: TextView = itemView.findViewById(R.id.toDo_date_item)
        private val checkedImage: ImageView = itemView.findViewById(R.id.checked_image)
        private val deleteImage: ImageView = itemView.findViewById(R.id.delete_image)

        init {
            itemView.setOnClickListener(this)

        }

        fun bind(toDoList: ToDoList) {
            this.toDoList = toDoList

            titleTextView.text = toDoList.title
            deleteImage.setOnClickListener {
                toDoListViewModel.deleteToDo(toDoList)
            }
            dateTextView.text = toDoList.dueDate.toString()

            checkedImage.visibility = if (toDoList.isCompleted) {

                View.VISIBLE
            } else {
                View.GONE


            }

            val currentDate = Date()


            if (toDoList.dueDate==null) {
                dateTextView.text = toDoList.creationDate.toString()

            }


            if (toDoList.dueDate != null) {



                if (currentDate.after(toDoList.dueDate)) {

                    Toast.makeText(
                        context,"you can make the task "
                        ,
                        Toast.LENGTH_LONG).show()


                } else if (currentDate.before(toDoList.dueDate)) {

                    Toast.makeText(
                        context,"${toDoList.dueDate}  you missed the task" , Toast.LENGTH_LONG
                    ).show()

                }






            }
        }

        override fun onClick(v: View?) {
            if (v == itemView) {
                val args = Bundle()
                args.putSerializable(KEY_ID, toDoList.id)
                val fragment = ToDoListFragment()
                fragment.arguments = args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

        }

    }

    private inner class ToDoAdapter(var toDoLists: List<ToDoList>) : RecyclerView.Adapter<ToDoHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoHolder {
            val view = layoutInflater.inflate(R.layout.list_item_blank, parent, false)
            return ToDoHolder(view)
        }

        override fun onBindViewHolder(holder: ToDoHolder, position: Int) {
            val toDoList = toDoLists[position]
            holder.bind(toDoList)

        }

        override fun getItemCount() = toDoLists.size

    }


}