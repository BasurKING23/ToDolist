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

    lateinit var binding: ActivityTaskBinding

    lateinit var taskDAO: TaskDAO


    val hintList = listOf(
        "Comprar leche",
        "Apartar mesa de ping pong",
        "Comprar pan",
        "Llevar a la niña en cola",
        "Ir a las tapas"
    )

    @SuppressLint("WrongViewCast", "CutPasteId")
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

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.titleEditText)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listOf("Opción 1", "Opción 2", "Opción 3"))
        autoCompleteTextView.setAdapter(adapter)


        binding.titleEditText.hint = hintList.random()

        taskDAO = TaskDAO(this)



        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            val task = Task(-1L,title, false)

            taskDAO.insert(task)

            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
        }

}