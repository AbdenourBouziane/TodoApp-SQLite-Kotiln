package com.example.todo_sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "todo.db"
        private const val TBL_TODO = "tbl_todo"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTblTodo = "CREATE TABLE $TBL_TODO " +
                "($ID INTEGER PRIMARY KEY, $TITLE TEXT, $DESCRIPTION TEXT)"
        db.execSQL(createTblTodo)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TBL_TODO")
        onCreate(db)
    }

    fun insertTodo(std: TodoModel): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(TITLE, std.title)
        contentValues.put(DESCRIPTION, std.description)
        val success = db.insert(TBL_TODO, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllTodo(): ArrayList<TodoModel> {
        val stdList: ArrayList<TodoModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_TODO"
        val db = this.readableDatabase

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            return ArrayList()
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndex(ID))
                    val title = cursor.getString(cursor.getColumnIndex(TITLE))
                    val description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                    val std = TodoModel(id = id, title = title, description = description)
                    stdList.add(std)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }

        return stdList
    }

    fun updateTodo(std: TodoModel): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID , std.id)
        contentValues.put(TITLE , std.title)
        contentValues.put(DESCRIPTION , std.description)

        val success = db.update(TBL_TODO , contentValues , "id=" + std.id , null)

        db.close()
        return success
    }

    fun deleteTodoById(id : Int) : Int{
        val db  = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID , id)

        val success = db.delete(TBL_TODO  , "id=$id", null)

        db.close()
        return success
    }
}
