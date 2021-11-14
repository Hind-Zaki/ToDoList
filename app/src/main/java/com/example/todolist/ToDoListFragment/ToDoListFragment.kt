package com.example.todolist.ToDoListFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.Todo.toDoListFragment.KEY_ID
import com.example.Todo.toDoListFragment.ToDoListListFragment
import com.example.todolist.Database.ToDoList
import com.example.todolist.DatePickerDialogFragment
import com.example.todolist.R
import java.util.*

const val TODOLIST_DATE_KEY = "toDoListDate"

class ToDoListFragment : Fragment(), DatePickerDialogFragment.DatePickerCallback {

    private lateinit var toDoList: ToDoList
    private lateinit var saveButton: Button
    private lateinit var titleEditText: EditText
    private lateinit var detailsEditText: EditText
    private lateinit var dateButton: Button
    private lateinit var creationDateTextView:TextView
    private lateinit var isCompletedCheckBox:CheckBox


    private val toDoListViewModel by lazy {
        ViewModelProvider(this).get(ToDoListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_to_do_list, container, false)
        saveButton = view.findViewById(R.id.toDoList_save_button)
        dateButton = view.findViewById(R.id.toDolist_date)
        titleEditText = view.findViewById(R.id.toDoList_title_hint_edit_text)
        isCompletedCheckBox=view.findViewById(R.id.completed_checkbox)
        detailsEditText = view.findViewById(R.id.toDo_details)
        creationDateTextView=view.findViewById(R.id.toDo_creation_date)




        return view
    }

    override fun onStart() {
        super.onStart()

        saveButton.setOnClickListener {
            toDoListViewModel.saveUpdate(toDoList)
            val fragment = ToDoListListFragment()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()

            }


        }
        dateButton.setOnClickListener {

            val args = Bundle()
            args.putSerializable(TODOLIST_DATE_KEY, toDoList.creationDate)
            val datePicker = DatePickerDialogFragment()
            datePicker.arguments = args
            datePicker.setTargetFragment(this, 0)
            datePicker
                .show(this.parentFragmentManager,"date picker");
        }

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                toDoList.title = sequence.toString()

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }

        titleEditText.addTextChangedListener(titleWatcher)


        val detailsWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

                toDoList.details = sequence.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }

        detailsEditText.addTextChangedListener(detailsWatcher)
        isCompletedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            toDoList.isCompleted= isChecked

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toDoList = ToDoList()
        arguments?.let {
           val toDoListId =it.getSerializable(KEY_ID) as UUID
           toDoListViewModel.loadToDoList(toDoListId)
        }



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoListViewModel.toDoListLiveData.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                    this.toDoList = it

                    titleEditText.setText(it.title)
                    detailsEditText.setText(it.details)
                    isCompletedCheckBox.isChecked=it.isCompleted
                    creationDateTextView.text= toDoList.creationDate.toString()



                }
            }
        )
    }

    override fun onDateSelected(date: Date) {
        toDoList.dueDate= date
        dateButton.text=date.toString()


    }

    override fun onStop() {
        super.onStop()
        toDoListViewModel.saveUpdate(toDoList)
    }


}


