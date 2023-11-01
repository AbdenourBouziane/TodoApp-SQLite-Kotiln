package com.example.todo_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var edTitle: EditText
    private lateinit var edDesc: EditText
    private lateinit var btnAdd : Button
    private lateinit var btnView : Button
    private lateinit var btnUpdate : Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : TodoAdapter ? = null

    private var std:TodoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener{addTodos()}
        btnView.setOnClickListener{getTodos()}
        btnUpdate.setOnClickListener{updateStudent()}
        adapter?.setOnClickItem {
            Toast.makeText(this, it.title , Toast.LENGTH_SHORT).show()
            edTitle.setText(it.title)
            edDesc.setText(it.description)
            std = it
        }

        adapter?.setOnClickDeleteItem { deleteTodo(it.id) }
    }

    private fun getTodos(){
        val stdList = sqLiteHelper.getAllTodo()
        Log.e("ppp" , "${stdList.size}")

        adapter?.addItems(stdList)
    }

    private fun addTodos(){
        val title = edTitle.text.toString()
        val description = edDesc.text.toString()

        if(title.isEmpty() || description.isEmpty()){
            Toast.makeText(this, "Please enter required field" , Toast.LENGTH_SHORT).show()
        }else{
            val std = TodoModel(title = title , description = description)
            val status = sqLiteHelper.insertTodo(std)
            //check insert success or not
            if(status > -1){
                Toast.makeText(this, "To do added..." , Toast.LENGTH_SHORT).show()
                clearEditText()
                getTodos()
            }else{
                Toast.makeText(this, "Record not saved" , Toast.LENGTH_SHORT).show()
            }
        }
    }
    private  fun updateStudent(){
        val title = edTitle.text.toString()
        val description = edDesc.text.toString()

        if (title == std?.title && description == std?.description){
            Toast.makeText(this, "Record did not changed" , Toast.LENGTH_SHORT).show()
            return
        }
        if (std == null) return

        val std = TodoModel(id = std!!.id , title = title , description = description )
        val status = sqLiteHelper.updateTodo(std)
        if(status > -1){
            clearEditText()
            getTodos()
        }else{
            Toast.makeText(this, "Update failed" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTodo(id : Int){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this Todo")
        builder.setCancelable(true)
        builder.setPositiveButton("No"){dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("Yes"){dialog, _ ->
            sqLiteHelper.deleteTodoById(id)
            getTodos()
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()

    }


    private fun clearEditText(){
        edTitle.setText("")
        edDesc.setText("")
        edTitle.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoAdapter()
        recyclerView.adapter =adapter
    }

    private fun initView(){
        edTitle = findViewById(R.id.edTitle)
        edDesc = findViewById(R.id.edDesc)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}