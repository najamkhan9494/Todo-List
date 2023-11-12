package com.assignment.todo.presentation.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.todo.R
import com.assignment.todo.data.model.CategoryItem
import com.assignment.todo.databinding.DialogFilterCategoriesBinding
import com.assignment.todo.presentation.adapter.CategoryAdapter

class CategoryFilterDialog(
    private val context: Context,
    private val categories: List<String>,
    private val onFilterApplied: (List<String>) -> Unit
) {

    private var selectedCategories: List<String> = mutableListOf()

    fun showFilterDialog() {
        val dialogBinding = DialogFilterCategoriesBinding.inflate(LayoutInflater.from(context))
        val recyclerView = dialogBinding.recyclerViewCategories

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.filter_by_Category)

        val categoryItemList = categories.map { CategoryItem(it, false) }
        val adapter = CategoryAdapter(categoryItemList) { categories ->
            selectedCategories = categories
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        builder.setView(dialogBinding.root)
            .setPositiveButton(R.string.apply) { _, _ ->
                onFilterApplied(selectedCategories)
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }
}
