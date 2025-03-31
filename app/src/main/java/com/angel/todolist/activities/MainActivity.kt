package com.angel.todolist.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.angel.todolist.R
import com.angel.todolist.adapter.TaskAdapter
import com.angel.todolist.data.Task
import com.angel.todolist.data.TaskDAO
import com.angel.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var taskDAO: TaskDAO
    lateinit var taskList: List<Task>

    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        taskDAO = TaskDAO(this)


        //editar tarea
        adapter = TaskAdapter(emptyList()) {

            val task = taskList[it]

            intent.putExtra("TASK_ID", task.id)

        }
        //editar tarea


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        //boton de añadir
        binding.addtaskbutton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)

        }
        //boton de añadir
    }

    override fun onResume() {
        super.onResume()

        taskList = taskDAO.findAll()
        adapter.updateItems(taskList)


    }
}