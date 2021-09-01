package com.example.listmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listmaker.TaskList

class TodoListAdapter(private val lists:ArrayList<TaskList>,val clickListener: TodoListClickListener) : RecyclerView.Adapter<TodoListViewHolder>(), TodoListClickListener   {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view =LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_list_view_holder,parent,false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {

        holder.listPositioTextView.text= (position+1).toString()
        holder.listTitleTextView.text= lists[position].name
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun addList(list: TaskList) {
        lists.add(list)
        notifyItemInserted(lists.size-1)
    }

    override fun listItemClicked(list: TaskList) {

    }

}

class TodoListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    var listPositioTextView=itemView.findViewById<TextView>(R.id.İtemNumber)
    var listTitleTextView=itemView.findViewById<TextView>(R.id.itemString)
}