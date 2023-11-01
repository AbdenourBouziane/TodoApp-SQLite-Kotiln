package com.example.todo_sqlite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.StudentViewHolder>() {

    private var stdList : ArrayList<TodoModel> = ArrayList()
    private var onClickItem : ((TodoModel) -> Unit)? = null
    private var onClickDeleteItem : ((TodoModel) -> Unit)? = null


    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items : ArrayList<TodoModel>){
        this.stdList = items
        notifyDataSetChanged()

    }

    fun setOnClickItem( callback: (TodoModel)->Unit){
        this.onClickItem = callback
    }
    fun setOnClickDeleteItem( callback: (TodoModel)->Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items , parent , false)
    )

    override fun getItemCount(): Int {
        return stdList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bidViews(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener{onClickDeleteItem?.invoke(std)}
    }

    class StudentViewHolder( view : View) : RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var title = view.findViewById<TextView>(R.id.tvTitle)
        private var description = view.findViewById<TextView>(R.id.tvDesc)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)


        fun bidViews(std : TodoModel){
            id.text = std.id.toString()
            title.text = std.title
            description.text = std.description
        }
    }
}