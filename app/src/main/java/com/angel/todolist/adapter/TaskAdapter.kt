package com.angel.todolist.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angel.todolist.data.Task
import com.angel.todolist.databinding.ItemTaskBinding
import com.angel.todolist.utils.addStrikethrough

class TaskAdapter(var items: List<Task>,
                  val onClick: (Int) -> Unit,
                  val onDelete: (Int) -> Unit,
                  val onCheck: (Int) -> Unit
): Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }
    // cuenta cuantos item tengo
    override fun getItemCount(): Int = items.size
    // fin de cuenta

    // muestra la vista del recicler view
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = items[position]
        holder.render(task)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
        holder.binding.deletebutton.setOnClickListener {
            onDelete(position)
        }
        holder.binding.doneCheckBox.setOnCheckedChangeListener { _, _ ->
        onCheck(position)
            if (holder.binding.doneCheckBox.isPressed) {
                onCheck(position)
            }
        }
    }
    // fin de  muestra la vista del recicler view


    //actualizacion de datos en la lista
    fun updateItems(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()
    }
    // fin de actualizacion de la lista
}


class TaskViewHolder(val binding: ItemTaskBinding) : ViewHolder(binding.root) {

    fun render(task: Task) {
        if (task.done) {
            binding.titleTextView.text = task.title.addStrikethrough()
        } else {
            binding.titleTextView.text = task.title
        }
        binding.doneCheckBox.isChecked = task.done
    }
}
