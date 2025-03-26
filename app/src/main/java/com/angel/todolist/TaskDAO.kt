package com.angel.todolist

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.core.app.TaskStackBuilder

class TaskDAO(context: Context) {

    val databaseManager = DatabaseManager(context)

    fun insert(task: Task){
        val db = databaseManager.writableDatabase

        val values = ContentValues().apply {
            put(Task.COLUMN_NAME_TITLE,task.title)
            put(Task.COLUMN_NAME_DONE,task.done)
        }

        try {
            val newRowId = db.insert(Task.TABLE_NAME,null,values)

            Log.i("DATABASE","Insert Task with id: $newRowId ")
        }
            catch (e : Exception){
                e.printStackTrace()
                }finally {
                    db.close()
                }
            }


    fun update(task: Task){

        fun update(task: Task){
            val db = databaseManager.writableDatabase

            val values = ContentValues().apply {
                put(Task.COLUMN_NAME_TITLE,task.title)
                put(Task.COLUMN_NAME_DONE,task.done)
            }

        try {
            val updateRow = db.update(Task.TABLE_NAME,values,"${Task.COLUMN_NAME_ID} = ${task.id}")

            Log.i("DATABASE","updated Task with id: ${task.id} ")
        }
        catch (e : Exception){
            e.printStackTrace()
        }finally {
            db.close()
        }


    }
    fun delete(task: Task){
        val db = databaseManager.writableDatabase

        try {
            val deleteRow = db.delete(Task.TABLE_NAME,values,"${Task.COLUMN_NAME_ID} = ${task.id}")

            Log.i("DATABASE","updated Task with id: ${task.id} ")
        }
        catch (e : Exception){
            e.printStackTrace()
        }finally {
            db.close()
        }
    }

    fun findById (id: Long):Task{

        val db = databaseManager.readableDatabase

        val projection = arrayOf(Task.COLUMN_NAME_ID,Task.COLUMN_NAME_TITLE,Task.COLUMN_NAME_DONE)

        val selection = "${Task.COLUMN_NAME_ID} = $id"


    }
    fun finnAll():Task{


    }

}
}