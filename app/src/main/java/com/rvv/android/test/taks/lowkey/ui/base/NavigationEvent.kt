package com.rvv.android.test.taks.lowkey.ui.base

import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

sealed class NavigationEvent {

    data class Forward(
        val directions: NavDirections,
        val navOptions: NavOptions? = null,
    ) : NavigationEvent()
}
