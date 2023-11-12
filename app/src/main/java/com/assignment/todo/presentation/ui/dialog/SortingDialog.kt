package com.assignment.todo.presentation.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.assignment.todo.R
import com.assignment.todo.databinding.DialogSortingBinding

class SortingDialog(
    private val context: Context,
    private val onSortingApplied: (SortOption) -> Unit
) {

    enum class SortOption {
        PRIORITY,
        DUE_DATE,
        COMPLETENESS
    }

    fun showSortingDialog() {
        val dialogBinding = DialogSortingBinding.inflate(LayoutInflater.from(context))
        val cbPriority = dialogBinding.cbPriority
        val cbDueDate = dialogBinding.cbDueDate
        val cbCompleteness = dialogBinding.cbCompleteness

        // Handle single selection logic
        val checkBoxes = listOf(cbPriority, cbDueDate, cbCompleteness)
        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    checkBoxes.filterNot { it == checkBox }.forEach {
                        it.isChecked = false
                    }
                }
            }
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.sort_by)

        builder.setView(dialogBinding.root)
            .setPositiveButton(R.string.apply) { _, _ ->
                val selectedOption = when {
                    cbPriority.isChecked -> SortOption.PRIORITY
                    cbDueDate.isChecked -> SortOption.DUE_DATE
                    cbCompleteness.isChecked -> SortOption.COMPLETENESS
                    else -> null
                }

                if (selectedOption != null) {
                    onSortingApplied(selectedOption)
                }
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }

        val dialog = builder.create()
        dialog.show()
    }


}
