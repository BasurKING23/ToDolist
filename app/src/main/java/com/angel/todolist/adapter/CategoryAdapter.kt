package com.angel.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angel.todolist.data.Category
import com.angel.todolist.databinding.ItemCategoryBinding
import com.angel.todolist.utils.addStrikethrough

class CategoryAdapter(
    var items: List<Category>,
    val onClick: (Int) -> Unit,
    val onEdit: (Int) -> Unit,
    val onDelete: (Int) -> Unit
) : Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = items[position]
        holder.render(category)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
        holder.itemView.setOnLongClickListener {
            onEdit(position)
            true
        }
        holder.binding.deleteButton.setOnClickListener {
            onDelete(position)
        }
    }

    fun updateItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class CategoryViewHolder(val binding: ItemCategoryBinding) : ViewHolder(binding.root) {

    fun render(category: Category) {
        binding.titleTextView.text = category.title
    }
}