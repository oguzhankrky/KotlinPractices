package com.example.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),TodoListClickListener {

    val listDataManager : ListDataManager= ListDataManager(this)
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var todoListRecyclerView:RecyclerView;

    companion object{
        const val INTENT_LIST_KEY="list"
        const val LIST_DETAIL_REQUEST_CODE =123
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        ActivityMainBinding()
        SetRcycleView()
        navController()
        clickAndCreateTodoListDialog()

    }

    private fun ActivityMainBinding()
    {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


    }

    private fun clickAndCreateTodoListDialog(){

        binding.fab.setOnClickListener { view ->
            /* val adapter=todoListRecyclerView.adapter as TodoListAdapter
             adapter.addNewItem()*/
            showCreateTodoListDialog()

        }

    }

    private fun SetRcycleView()
    {
        var lists =listDataManager.readList()
        todoListRecyclerView=findViewById(R.id.list_RecyclerView)
        todoListRecyclerView.layoutManager=LinearLayoutManager(this)
        todoListRecyclerView.adapter=TodoListAdapter(lists,this)
    }

    private fun navController()
    {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun showCreateTodoListDialog() {

        val dialogTitle = getString(R.string.name_Of_List)
        val positiveButtonTitle = getString(R.string.Create_list)
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveButtonTitle) {
                dialog, _ ->
            val adapter = todoListRecyclerView.adapter as TodoListAdapter
            val list = TaskList(todoTitleEditText.text.toString())
            listDataManager.saveList(list)
            adapter.addList(list)

            dialog.dismiss()
            showTaskListItems(list)
        }
        myDialog.create().show()
    }
    private fun showTaskListItems(list:TaskList){
        val taskListItem=Intent(this,DetailActivity::class.java)
        taskListItem.putExtra(INTENT_LIST_KEY,list)
        startActivityForResult(taskListItem, LIST_DETAIL_REQUEST_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== LIST_DETAIL_REQUEST_CODE)
        {
            data?.let {
                val list =data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)
                listDataManager.saveList(list)
                updateList()
            }
        }
    }

    private fun updateList() {
        val lists = listDataManager.readList()
        todoListRecyclerView.adapter = TodoListAdapter(lists,this)
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
    }
}