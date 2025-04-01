package com.angel.todolist.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        taskDAO = TaskDAO(this)
        //editar tarea
            adapter = TaskAdapter(emptyList(), ::editTask, ::deleteTask, ::checkTask)
            binding.recyclerView.adapter = adapter

            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //editar tarea


        //boton de añadir
        binding.addtaskbutton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)

        }
        //boton de añadir
    }

    override fun onResume() {
        super.onResume()

        refreshData()

    }
    fun refreshData() {
        taskList = taskDAO.findAll()
        adapter.updateItems(taskList)

        if (taskList.isEmpty()) {
            binding.TaskEmpty.visibility = View.VISIBLE
        } else {
            binding.TaskEmpty.visibility = View.GONE
        }
    }
    fun checkTask(position: Int) {
        val task = taskList[position]

        task.done = !task.done
        taskDAO.update(task)
        refreshData()
    }
    fun editTask (position: Int) {
        val task = taskList [position]
        val intent = Intent(this, TaskActivity::class.java)
        intent.putExtra(TaskActivity.TASK_ID,task.id)
        startActivity(intent)
    }
    fun deleteTask (position: Int) {
        val task = taskList[position]

        AlertDialog.Builder(this)
            .setTitle("Delete task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                taskDAO.delete(task)
                refreshData()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setCancelable(false)
            .show()
    }
}
