package com.angel.todolist.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.angel.todolist.data.Category
import com.angel.todolist.data.Task

class DatabaseManager (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) { //se crea la base de datos y la version

    companion object {
        const val DATABASE_NAME = "reminders.db"
        const val DATABASE_VERSION = 2                                          //se crea la version de la base de datos y el nombre de la base de datos

        private const val SQL_CREATE_TABLE_TASK =
            //se crea la tabla
            "CREATE TABLE ${Task.TABLE_NAME} (" +
                    "${Task.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Task.COLUMN_NAME_TITLE} TEXT," +
                    "${Task.COLUMN_NAME_DONE} BOOLEAN," +
                    "${Task.COLUMN_NAME_CATEGORY} INTEGER, " +
                    "FOREIGN KEY(${Task.COLUMN_NAME_CATEGORY}) " +
                    "REFERENCES ${Category.TABLE_NAME}(${Category.COLUMN_NAME_ID}) ON DELETE CASCADE)"

        private const val SQL_DROP_TABLE_TASK = "DROP TABLE IF EXISTS ${Task.TABLE_NAME}"           //se elimina la tabla

    private const val SQL_CREATE_TABLE_CATEGORY =
        "CREATE TABLE ${Category.TABLE_NAME} (" +
                "${Category.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Category.COLUMN_NAME_TITLE} TEXT)"

    private const val SQL_DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS ${Category.TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {                                                     //se crean las tablas
        db.execSQL(SQL_CREATE_TABLE_CATEGORY)
        db.execSQL(SQL_CREATE_TABLE_TASK)
        Log.i("DATABASE", "Created table Tasks")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {                  //se actualiza la tabla
        onDestroy(db)
        onCreate(db)
    }
    private fun onDestroy(db: SQLiteDatabase) {                                                      //se destruye la tabla
        db.execSQL(SQL_DROP_TABLE_TASK)
        db.execSQL(SQL_DROP_TABLE_CATEGORY)
    }
}