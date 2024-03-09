package com.rvv.android.test.taks.lowkey.utils

import android.content.Context

fun Context.dpToPx(dp: Int): Float = dp * resources.displayMetrics.density
fun Context.dpToPxInt(dp: Int): Int = dpToPx(dp).toInt()
