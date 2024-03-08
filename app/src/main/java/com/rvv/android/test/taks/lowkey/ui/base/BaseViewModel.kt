package com.rvv.android.test.taks.lowkey.ui.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.rvv.android.test.taks.lowkey.utils.launchAndCollectIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    protected fun navigate(event: NavigationEvent) = _navigationEvent.tryEmit(event)
    protected fun forward(direction: NavDirections) = navigate(NavigationEvent.Forward(direction))
}

fun BaseViewModel.observeCommonEvents(
    fragment: Fragment,
    lifecycleOwner: LifecycleOwner = fragment.viewLifecycleOwner,
) {
    navigationEvent.launchAndCollectIn(lifecycleOwner) { event ->
        val navController = fragment.findNavController()
        when (event) {
            is NavigationEvent.Forward -> navController.navigate(event.directions, event.navOptions)
        }
    }
}