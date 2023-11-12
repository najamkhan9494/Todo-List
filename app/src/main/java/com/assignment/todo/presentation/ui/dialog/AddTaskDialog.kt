package com.assignment.todo.presentation.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.assignment.todo.R
import com.assignment.todo.data.model.Task
import com.assignment.todo.databinding.DialogAddTaskBinding
import com.assignment.todo.utils.DateUtils
import java.util.Calendar
import java.util.UUID

class AddTaskDialog(private val context: Context, private val callback: (Task) -> Unit) {

    private val alertBinding: DialogAddTaskBinding =
        DialogAddTaskBinding.inflate(LayoutInflater.from(context))

    init {
        val priorityArray = context.resources.getStringArray(R.array.priority_levels)
        val priorityAdapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, priorityArray)
        alertBinding.spPriority.adapter = priorityAdapter
    }

    fun showAddTaskDialog(task: Task? = null) {
        if (task != null) {
            // Pre-populate the dialog fields with task data in case of edition
            alertBinding.etTitle.setText(task.title)
            alertBinding.etCategory.setText(task.category)
            alertBinding.etDescription.setText(task.description)
            alertBinding.etDate.setText(task.date)
            alertBinding.spPriority.setSelection(
                context.resources.getStringArray(R.array.priority_levels)
                    .indexOf(task.priority)
            )
        }

        val builder = AlertDialog.Builder(context)
            .setView(alertBinding.root)
            .setTitle(if (task != null) context.getString(R.string.update_task) else context.getString(R.string.add_task))
            .setPositiveButton(if (task != null) context.getString(R.string.update_task) else context.getString(R.string.add_task)) { _, _ ->
                val title = alertBinding.etTitle.text.toString()
                val category = alertBinding.etCategory.text.toString()
                val description = alertBinding.etDescription.text.toString()
                val date = alertBinding.etDate.text.toString()
                val priority = alertBinding.spPriority.selectedItem.toString()

                if (title.isNotEmpty() && category.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()) {
                    val addTask = // If task is not null, it means we are updating an existing task
                        task?.copy(
                            title = alertBinding.etTitle.text.toString(),
                            category = alertBinding.etCategory.text.toString(),
                            description = alertBinding.etDescription.text.toString(),
                            date = alertBinding.etDate.text.toString(),
                            priority = alertBinding.spPriority.selectedItem.toString()
                        )
                            ?: // If task is null, it means we are adding a new task
                            Task(
                                title = title,
                                category = category,
                                description = description,
                                completed = false,
                                userId = generateRandomUserId(),
                                date = date,
                                priority = priority
                            )
                    callback.invoke(addTask)
                } else {
                    Toast.makeText(
                        context,
                        context.getText(R.string.all_field_required),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(context.getString(R.string.no)) { _, _ -> }

        builder.create().show()

        alertBinding.etDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(context, { _, year, month, day ->
            calendar.set(year, month, day)
            alertBinding.etDate.setText(DateUtils.formatToDateString(calendar.time))
        }, year, month, day)

        datePickerDialog.show()
    }

    fun generateRandomUserId(): Int {
        // Generate a random user ID using UUID we can update it according to our logic
        return UUID.randomUUID().hashCode()
    }
}
