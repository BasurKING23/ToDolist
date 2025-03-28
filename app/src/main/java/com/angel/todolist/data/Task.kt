package com.angel.todolist.data

class Task (
    var id: Long,
    var title: String,
    var done: Boolean = false


) {
    companion object {
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DONE = "done"
        const val TABLE_NAME = "Tasks"
    }
}