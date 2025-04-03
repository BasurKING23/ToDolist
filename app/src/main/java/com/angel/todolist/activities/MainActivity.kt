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
import com.angel.todolist.adapter.CategoryAdapter
import com.angel.todolist.data.Category
import com.angel.todolist.data.CategoryDAO
import com.angel.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding //se crea el binding para la vista de la actividad

    lateinit var categoryDAO: CategoryDAO //se crea el DAO para la tabla de categorias
    lateinit var categoryList: List<Category> //se crea la lista de categorias
    lateinit var adapter: CategoryAdapter //se crea el adapter para la lista de categorias

//    lateinit var categoryDAO: CategoryDAO
//    lateinit var categoryList: List<Category>

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

        categoryDAO = CategoryDAO(this)     //se crea el DAO para la tabla de categorias y se crea la lista de categorias

        supportActionBar?.title = "Mis categorias" //se cambia el titulo de la actividad

        //editar tarea
            adapter = CategoryAdapter(emptyList(), ::showCategory, ::editCategory, ::deleteCategory)

            binding.recyclerView.adapter = adapter   //se crea el adapter para la lista de categorias

            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //editar tarea


        // boton de añadir categoria


        binding.addCategoryButton.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        // boton de añadir categoria

    }

    override fun onResume() {
        super.onResume()

        refreshData()

    }
    fun refreshData() {
        categoryList = categoryDAO.findAll()
        adapter.updateItems(categoryList)

        if (categoryList.isEmpty()) {
            binding.CategoryEmpty.visibility = View.VISIBLE     //se muestra el mensaje de que no hay categorias
        } else {
            binding.CategoryEmpty.visibility = View.GONE  // se oculta el mensaje de que no hay categorias
        }
    }
    fun editCategory (position: Int) {
        val category = categoryList [position]

        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CategoryActivity.CATEGORY_ID,category.id)
        startActivity(intent)
    }
    fun deleteCategory (position: Int) {
        val category = categoryList[position]

        AlertDialog.Builder(this)
            .setTitle("Delete category")
            .setMessage("Are you sure you want to delete this category?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                categoryDAO.delete(category)
                refreshData()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setCancelable(false)
            .show()
    }
}
