package com.assignment.todo.presentation.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.assignment.todo.R

class ConfirmationAlertDialog(
    private val context: Context,
    private val title: String,
    private val message: String,
    private val onConfirmed: () -> Unit
) {

    fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)

        builder.setPositiveButton(context.getString(R.string.yes)) { _, _ ->
            onConfirmed.invoke()
        }
        builder.setNegativeButton(context.getString(R.string.no)) { _, _ -> }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
