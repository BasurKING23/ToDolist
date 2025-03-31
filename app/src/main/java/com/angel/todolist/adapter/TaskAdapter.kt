package com.angel.todolist.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.angel.todolist.data.Task
import com.angel.todolist.databinding.ItemTaskBinding

class TaskAdapter(var items: List<Task>,
                  val onClick: (Int) -> Unit,
                  var items: List<Task>,
                  val onDelete: (Int) -> Unit
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
        binding.titleTextView.text = task.title
        binding.doneCheckBox.isChecked = task.done
    }
}