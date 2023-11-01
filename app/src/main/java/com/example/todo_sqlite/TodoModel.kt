package com.example.todo_sqlite

import java.util.*

data class TodoModel(
    var id : Int = getAutoID(),
    var title: String = "",
    var description : String = ""
){
    companion object {
        fun getAutoID(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}
