package com.assignment.todo.presentation.ui

import android.os.Bundle
import com.assignment.todo.R
import com.assignment.todo.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.todo.data.model.Task
import com.assignment.todo.databinding.ActivityMainBinding
import com.assignment.todo.presentation.adapter.TaskAdapter
import com.assignment.todo.presentation.ui.dialog.AddTaskDialog
import com.assignment.todo.presentation.ui.dialog.CategoryFilterDialog
import com.assignment.todo.presentation.ui.dialog.ConfirmationAlertDialog
import com.assignment.todo.presentation.ui.dialog.SortingDialog
import com.assignment.todo.utils.AppUtils
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        // Fetch data and store locally
        viewModel.fetchDataAndStoreLocally()
        adapterSetup()
        observers()
        viewListners()
    }
    private fun viewListners() {
        binding.fbAddTask.setOnClickListener { onAddTaskClick() }
        binding.ivFilter.setOnClickListener { showFilterDialog() }
        binding.ivSort.setOnClickListener { showSortingDialog() }

        binding.ivClear.setOnClickListener {
            adapter.clearFilters()
            binding.svTask.setQuery("", false)
            binding.svTask.clearFocus()
        }
        binding.svTask.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filterTasksBySearchQuery(newText.orEmpty())
                return true
            }
        })
    }


    private fun adapterSetup() {
        adapter = TaskAdapter(
            context = this,
            onTaskItemClick = { task -> onTaskItemClick(task) },
            onEditClick = { task -> onEditClick(task) },
            onDeleteClick = { task -> onDeleteClick(task) },
            onCompleteClick = { task -> onCompleteClick(task) }
        )
        binding.rvTasks.layoutManager = LinearLayoutManager(this)
        binding.rvTasks.adapter = adapter
    }
    private fun observers() {
        // Observe the task list
        viewModel.taskList.observe(this) { tasks ->
            adapter.tasks = tasks
        }
    }

    private fun showFilterDialog() {
        lifecycleScope.launch {
            val uniqueCategories = viewModel.getUniqueCategories()
            val categoryFilterAlert =
                CategoryFilterDialog(this@MainActivity, uniqueCategories) {
                    adapter.filterTasksByCategory(it)
                }
            categoryFilterAlert.showFilterDialog()
        }
    }

    private fun showSortingDialog() {
        val sortingDialog = SortingDialog(this) { sortOption ->
            adapter.sortTasks(sortOption)
        }
        sortingDialog.showSortingDialog()

    }

    private fun onTaskItemClick(task: Task) {
        // Handle task item click
    }

    private fun onEditClick(task: Task) {
        val taskDialog = AddTaskDialog(this) { updatedTask ->
            lifecycleScope.launch {
                viewModel.updateTask(updatedTask)
            }
            AppUtils.showSnackbar(this, getString(R.string.task_updated, task.title))
        }
        taskDialog.showAddTaskDialog(task)

    }

    private fun onDeleteClick(task: Task) {
        val deleteDialog = ConfirmationAlertDialog(
            this,
            getString(R.string.task_delete),
            getString(R.string.task_delete_msg)
        ) {
            lifecycleScope.launch {
                viewModel.deleteTask(task)
            }
            AppUtils.showSnackbar(this, getString(R.string.task_deleted, task.title))
        }
        deleteDialog.showConfirmationDialog()
    }

    private fun onCompleteClick(task: Task) {
        val completeDialog = ConfirmationAlertDialog(
            this,
            getString(R.string.task_complete),
            getString(R.string.task_completed_msg)
        ) {
            lifecycleScope.launch {
                viewModel.markTaskAsCompleted(task)
            }
            AppUtils.showSnackbar(this, getString(R.string.task_completed, task.title))
        }
        completeDialog.showConfirmationDialog()
    }


    private fun onAddTaskClick() {
        val taskDialog = AddTaskDialog(this) { newTask ->
            lifecycleScope.launch {
                viewModel.addTask(newTask)
            }
            AppUtils.showSnackbar(this, getString(R.string.task_added, newTask.title))

        }
        taskDialog.showAddTaskDialog()
    }

}
