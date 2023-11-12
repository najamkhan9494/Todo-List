package com.assignment.todo.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.assignment.todo.R
import com.assignment.todo.data.model.Task
import com.assignment.todo.databinding.ItemTaskBinding
import com.assignment.todo.presentation.ui.dialog.SortingDialog

class TaskAdapter(
    private val context: Context,
    private val onTaskItemClick: (Task) -> Unit,
    private val onEditClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit,
    private val onCompleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var tasks: List<Task> = emptyList()
        set(value) {
            field = value
            filterTasksByCategory()
            notifyDataSetChanged()
        }

    private var filteredTasks: List<Task> = tasks

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding, context)
    }

    override fun getItemCount(): Int = filteredTasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = filteredTasks[position]
        holder.bind(task)
        holder.itemView.setOnClickListener { onTaskItemClick(task) }
        holder.btnEdit.setOnClickListener { onEditClick(task) }
        holder.btnDelete.setOnClickListener { onDeleteClick(task) }
        holder.btnComplete.setOnClickListener { onCompleteClick(task) }
    }

    class TaskViewHolder(private val binding: ItemTaskBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        val btnEdit: ImageButton = binding.btnEdit
        val btnDelete: ImageButton = binding.btnDelete
        val btnComplete: ImageButton = binding.btnComplete

        fun bind(task: Task) {
            binding.tvTitle.text = context.getString(R.string.title_placeholder, task.title)
            binding.tvCategory.text = context.getString(R.string.category_placeholder, task.category)
            binding.tvDescription.text = context.getString(R.string.description_placeholder, task.description)
            binding.tvDate.text = context.getString(R.string.date_placeholder, task.date)
            binding.tvPriority.text = context.getString(R.string.priority_placeholder, task.priority)
        }
    }


    fun filterTasksByCategory(categories: List<String> = emptyList()) {
        filteredTasks = if (categories.isEmpty()) {
            tasks
        } else {
            tasks.filter { task -> categories.contains(task.category) }
        }
        notifyDataSetChanged()

    }

    fun filterTasksBySearchQuery(query: String) {
        filteredTasks = if (query.isEmpty()) {
            tasks
        } else {
            tasks.filter {
                it.title.contains(query, true) ||
                it.category.contains(query, true) ||
                it.priority.contains(query, true) ||
                it.description.contains(query, true)
            }
        }
        notifyDataSetChanged()
    }

    fun clearFilters() {
        filterTasksByCategory()
    }

    fun sortTasks(sortOption: SortingDialog.SortOption? = null) {
        when (sortOption) {
            SortingDialog.SortOption.PRIORITY -> sortTasksByPriority()
            SortingDialog.SortOption.DUE_DATE -> sortTasksByDueDate()
            SortingDialog.SortOption.COMPLETENESS -> sortTasksByCompleteness()
            else -> notifyDataSetChanged()
        }
    }

    private fun sortTasksByPriority() {
        val priorityLevels = context.resources.getStringArray(R.array.priority_levels)

        val priorityOrder = priorityLevels.withIndex().associate { (index, priority) -> priority to index }

        filteredTasks = filteredTasks.sortedBy { task ->
            priorityOrder[task.priority] ?: Int.MAX_VALUE
        }

        notifyDataSetChanged()
    }

    private fun sortTasksByDueDate() {
        filteredTasks = filteredTasks.sortedBy { it.date }
        notifyDataSetChanged()
    }

    private fun sortTasksByCompleteness() {
        filteredTasks = filteredTasks.sortedByDescending { it.completed }
        notifyDataSetChanged()
    }

}
