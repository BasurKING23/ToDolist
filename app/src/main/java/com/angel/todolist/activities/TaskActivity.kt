package com.angel.todolist.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angel.todolist.R
import com.angel.todolist.data.Task
import com.angel.todolist.data.TaskDAO
import com.angel.todolist.databinding.ActivityTaskBinding


class TaskActivity : AppCompatActivity() {

    companion object {
        const val TASK_ID = "TASK_ID"
    }

    lateinit var binding: ActivityTaskBinding

    lateinit var taskDAO: TaskDAO
    lateinit var task: Task

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
        val id = intent.getLongExtra(TASK_ID, -1L)

        taskDAO = TaskDAO(this)

        if (id != -1L) {
            task = taskDAO.findById(id)!!
            binding.titleEditText.setText(task.title)
        } else {
            task = Task(-1L, "")
        }


        // boton de guardar
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            val task = Task(-1L,title, false)

            taskDAO.insert(task)

            finish()
        }
        // boton de guardar

        //bototon de editar y guardar
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            task.title = title

            if (task.id != -1L) {
                taskDAO.update(task)
            } else {
                taskDAO.insert(task)
            }

            finish()
        }
        //botn de editar y guardar

    }

    //boton de retroceso con la appbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
        }
    //boton de retroceso con la appbar

}