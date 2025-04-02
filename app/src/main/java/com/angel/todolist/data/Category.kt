package com.angel.todolist.data

data class Category(val id: Long,
                    var title: String
)


    companion object {
        const val TABLE_NAME = "Category"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DONE = "done"


    }
