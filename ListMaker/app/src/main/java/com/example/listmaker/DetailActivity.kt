package com.example.listmaker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.databinding.ActivityDetailBinding
import com.example.listmaker.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {
    lateinit var list : TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SetContentView()
        setupRecyclerView()
        addTaskButton()

    }


    private fun setupRecyclerView(){
        list = intent.getParcelableExtra<TaskList>(MainActivity.INTENT_LIST_KEY) as TaskList
        setTitle(list.name)
        /*
        binding = DataBindingUtil.setContentView(this, R.id.task_list_recyclerview)
        binding.taskListRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.taskListRecyclerview.adapter=TaskListAdapter(list)

        */
        taskListRecyclerView =findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter=TaskListAdapter(list)
    }
    private fun SetContentView(){
        setContentView(R.layout.activity_detail)
    }
    private fun setTitle(name: String)
    {
        title =name
    }
    private fun addTaskButton(){
        addTaskButton =findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener{
            showCreateTaskDialog()
        }
    }
    override fun onBackPressed() {

        val bundle=Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY,list)
        val intent =Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK,intent)
        super.onBackPressed()
    }

    private fun showCreateTaskDialog() {
       val taskEditText=EditText(this)
        taskEditText.inputType=InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) {
                    dialog, _->
                val task =taskEditText.text.toString()
                list.tasks.add(task)
                dialog.dismiss()
            }
            .create()
            .show()
    }
}