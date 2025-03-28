package com.angel.todolist.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.angel.todolist.utils.DatabaseManager

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
            val updateRow = db.update(Task.TABLE_NAME,values, "${Task.COLUMN_NAME_ID} = ${task.id}", null)

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
            val deleteRow = db.delete(Task.TABLE_NAME, "${Task.COLUMN_NAME_ID} = ${task.id}", null)

            Log.i("DATABASE","updated Task with id: ${task.id} ")
        }
        catch (e : Exception){
            e.printStackTrace()
        }finally {
            db.close()
        }
    }

        fun findById(id: Long): Task? {
            val db = databaseManager.readableDatabase

            val projection = arrayOf(
                Task.COLUMN_NAME_ID,
                Task.COLUMN_NAME_TITLE,
                Task.COLUMN_NAME_DONE
            )

            val selection = "${Task.COLUMN_NAME_ID} = $id"

            var task: Task? = null

            try {
                val cursor = db.query(
                    Task.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
                )

                if (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
                    val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) != 0

                    task = Task(id, title, done)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.close()
            }

            return task
        }

        @SuppressLint("Recycle")
        fun findAll(): List<Task> {
            val db = databaseManager.readableDatabase

            val projection = arrayOf(
                Task.COLUMN_NAME_ID,
                Task.COLUMN_NAME_TITLE,
                Task.COLUMN_NAME_DONE
            )

            var taskList: MutableList<Task> = mutableListOf()

            try {
                val cursor = db.query(
                    Task.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
                )

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
                    val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) != 0

                    val task = Task(id, title, done)
                    taskList.add(task)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.close()
            }

            return taskList
        }
    }

}
