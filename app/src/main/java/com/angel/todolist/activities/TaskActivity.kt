package com.angel.todolist.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angel.todolist.R
import com.angel.todolist.data.Category
import com.angel.todolist.data.CategoryDAO
import com.angel.todolist.data.Task
import com.angel.todolist.data.TaskDAO
import com.angel.todolist.databinding.ActivityTaskBinding


class TaskActivity : AppCompatActivity() {

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
        const val TASK_ID = "TASK_ID"
    }

    lateinit var binding: ActivityTaskBinding

    lateinit var taskDAO: TaskDAO
    lateinit var task: Task
    lateinit var categoryDAO: CategoryDAO
    lateinit var category: Category


        //creacion de hint variable
    val hintList = listOf(
        "Comprar leche",
        "Apartar mesa de ping pong",
        "Comprar pan",
        "Llevar a la niña en cola",
        "Ir a las tapas"
    )
    //creacion de hint variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //setContentView(R.layout.activity_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //ramdon hint
        binding.titleEditText.hint = hintList.random()
        // fin de ramdon hint


        val id = intent.getLongExtra(TASK_ID, -1L)                          //se obtiene el id de la tarea si existe o se crea una nueva tarea si no existe
        val categoryId = intent.getLongExtra(CATEGORY_ID, -1L)              //se obtiene el id de la categoria si existe o se crea una nueva categoria si no existe

        taskDAO = TaskDAO(this)                     //se crea el DAO para la tabla de tareas y se obtiene la tarea de la base de datos si existe o se crea una nueva tarea si no existe
        categoryDAO = CategoryDAO(this)

        category = categoryDAO.findById(categoryId)!!                   //se obtiene la categoria de la base de datos si existe o se crea una nueva categoria si no existe

        if (id != -1L) {                                                // se obtiene la tarea de la base de datos si existe o se crea una nueva tarea si no existe
            task = taskDAO.findById(id)!!                               //se obtiene la tarea de la base de datos si existe o se crea una nueva tarea si no existe
            binding.titleEditText.setText(task.title)                   //se obtiene el titulo de la tarea si existe o se crea una nueva tarea si no existe
        } else {
            task = Task(-1L, "")                                //se crea una nueva tarea si no existe o se obtiene la tarea de la base de datos si existe
            supportActionBar?.title = "Crear tarea"
        }

        // boton de guardar


        binding.saveButton.setOnClickListener {

            val title = binding.titleEditText.text.toString()

            val task = Task(-1L,title, false)

            taskDAO.insert(task)

            finish()
        }
        // boton de guardar

        // boton de editar y guardar con la appbar y la base de datos para  verificar el ID
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            task.title = title

            if (task.id != -1L) {
                taskDAO.update(task)
            } else {
                taskDAO.insert(task)
            }

            finish()
            //boton de editar y guardar con la appbar y la base de datos para  verificar el ID
        }
    }

    //boton de retroceso con la appbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
    //boton de retroceso con la appbar

}