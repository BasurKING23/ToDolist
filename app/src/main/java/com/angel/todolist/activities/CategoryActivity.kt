package com.angel.todolist.activities


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.angel.todolist.R
import com.angel.todolist.activities.TaskActivity.Companion
import com.angel.todolist.data.Category
import com.angel.todolist.data.CategoryDAO
import com.angel.todolist.data.Task
import com.angel.todolist.databinding.ActivityCategoryBinding
import com.angel.todolist.databinding.ActivityTaskBinding

class CategoryActivity: AppCompatActivity() {       //se crea la actividad para la categoria y se hereda de AppCompatActivity

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
    }

    lateinit var binding: ActivityCategoryBinding

    lateinit var categoryDAO: CategoryDAO
    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        categoryDAO = CategoryDAO(this)

        val id = intent.getLongExtra(CATEGORY_ID, -1L)

        if (id != -1L) {
            category = categoryDAO.findById(id)!!
            binding.titleEditText.setText(category.title)
            supportActionBar?.title = "Editar categoría"
        } else {
            category = Category(-1L, "")
            supportActionBar?.title = "Crear tarea"
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            category.title = title

            if (category.id != -1L) {
                categoryDAO.update(category)
            } else {
                categoryDAO.insert(category)
            }

            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}