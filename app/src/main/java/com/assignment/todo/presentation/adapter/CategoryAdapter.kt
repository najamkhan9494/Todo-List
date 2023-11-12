package com.assignment.todo.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assignment.todo.data.model.CategoryItem
import com.assignment.todo.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categoryList: List<CategoryItem>,
    private val onCategorySelected: (List<String>) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val selectedCategories: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = categoryList[position]
        holder.bind(categoryItem)

        holder.binding.cbCategory.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedCategories.add(categoryItem.category)
            } else {
                selectedCategories.remove(categoryItem.category)
            }
            onCategorySelected(selectedCategories)
        }

    }

    class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryItem: CategoryItem) {
            binding.cbCategory.isChecked = categoryItem.isSelected
            binding.tvCategory.text = categoryItem.category
        }
    }
}

