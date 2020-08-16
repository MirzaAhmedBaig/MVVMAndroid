package com.mab.mvvmandroid.extensions.view

import android.view.View

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeHidden() {
    visibility = View.GONE
}