package com.assignment.todo.utils

import android.app.Activity
import com.google.android.material.snackbar.Snackbar

object AppUtils {

    fun showSnackbar(activity: Activity, message: String) {
        Snackbar.make(
            activity.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }


}

