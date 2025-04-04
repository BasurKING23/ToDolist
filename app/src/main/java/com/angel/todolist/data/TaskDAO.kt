package com.angel.todolist.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.angel.todolist.utils.DatabaseManager

class TaskDAO(context: Context) {

    val databaseManager = DatabaseManager(context)

    val categoryDAO = CategoryDAO(context)

    fun insert(task: Task) {                                        //se crea la funcion para insertar una tarea en la base de datos
        val db = databaseManager.writableDatabase

        val values = ContentValues().apply {                            //se crea un objeto ContentValues para almacenar los valores de la tarea
            put(Task.COLUMN_NAME_TITLE, task.title)
            put(Task.COLUMN_NAME_DONE, task.done)
            put(Task.COLUMN_NAME_CATEGORY, task.category.id)                //se obtiene el id de la categoria de la tarea y se almacena en la base de datos
        }

        try {
            val newRowId = db.insert(Task.TABLE_NAME, null, values)         //se inserta la tarea en la base de datos y se obtiene el id de la tarea insertada

            Log.i("DATABASE", "Insert Task with id: $newRowId ")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }


    fun update(task: Task) {                                //se crea la funcion para actualizar una tarea en la base de datos
        val db = databaseManager.writableDatabase

        val values = ContentValues().apply {                            // se crea un objeto ContentValues para almacenar los valores de la tarea
            put(Task.COLUMN_NAME_TITLE, task.title)
            put(Task.COLUMN_NAME_DONE, task.done)
            put(Task.COLUMN_NAME_CATEGORY, task.category.id)            // se obtiene el id de la categoria de la tarea y se almacena en la base de datos
        }

        try {
            val updateRow =                                 //se actualiza la tarea en la base de datos y se obtiene el numero de filas actualizadas
                db.update(Task.TABLE_NAME, values, "${Task.COLUMN_NAME_ID} = ${task.id}", null)

            Log.i("DATABASE", "updated Task with id: ${task.id} ")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }


    }

    fun delete(task: Task) {
        val db = databaseManager.writableDatabase  //se elimina una tarea de la base de datos y se obtiene el numero de filas eliminadas

        try {
            val deleteRow =
                db.delete(Task.TABLE_NAME, "${Task.COLUMN_NAME_ID} = ${task.id}", null)

            Log.i("DATABASE", "updated Task with id: ${task.id} ")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun findById(id: Long): Task? {                             //se crea la funcion para buscar una tarea por su id en la base de datos
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
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
                val done =
                    cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) != 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return task
    }



    fun findAll(): List<Task> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Task.COLUMN_NAME_ID,
            Task.COLUMN_NAME_TITLE,
            Task.COLUMN_NAME_DONE,
            Task.COLUMN_NAME_CATEGORY
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
                Task.COLUMN_NAME_DONE               // The sort order
            )

            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
                val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) != 0
                val categoryId = cursor.getLong(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_CATEGORY))
                val category = categoryDAO.findById(categoryId)!!
                val task = Task(id, title, done, category)

                taskList.add(task)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return taskList
    }

    fun findAllByCategory(category: Category): List<Task> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(
            Task.COLUMN_NAME_ID,
            Task.COLUMN_NAME_TITLE,
            Task.COLUMN_NAME_DONE,
            Task.COLUMN_NAME_CATEGORY
        )

        val selection = "${Task.COLUMN_NAME_CATEGORY} = ${category.id}"

        var taskList: MutableList<Task> = mutableListOf()

        try {
            val cursor = db.query(
                Task.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                Task.COLUMN_NAME_DONE               // The sort order
            )

            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
                val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) != 0
                val categoryId = cursor.getLong(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_CATEGORY))
                val category = categoryDAO.findById(categoryId)!!
                val task = Task(id, title, done, category)

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