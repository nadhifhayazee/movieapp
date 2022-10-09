package com.nadhif.hayazee.common.extension

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.nadhif.hayazee.common.R


fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.showErrorSnackBar(msg: String?) {
    Snackbar.make(this, msg ?: "", Snackbar.LENGTH_LONG).apply {
        val tv =
            this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        tv?.setTextColor(Color.WHITE)
        this.view.setBackgroundColor(
            ContextCompat.getColor(
                context,
                R.color.red_error
            )
        )
    }.show()
}