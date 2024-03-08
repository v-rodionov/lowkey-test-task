package com.rvv.android.test.taks.lowkey.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ViewFlipper
import com.rvv.android.test.taks.lowkey.databinding.StateViewFlipperStateErrorBinding
import com.rvv.android.test.taks.lowkey.databinding.StateViewFlipperStateProgressBinding

class StateViewFlipper(context: Context, attrs: AttributeSet? = null) : ViewFlipper(context, attrs) {

    private val progressViewBinding = StateViewFlipperStateProgressBinding.inflate(LayoutInflater.from(context))
    private val errorViewBinding = StateViewFlipperStateErrorBinding.inflate(LayoutInflater.from(context))

    enum class State(val displayedChild: Int) {
        LOADING(0),
        ERROR(1),
        DATA(2),
    }

    private var currentState = State.LOADING

    init {
        if (!isInEditMode) {
            addView(progressViewBinding.root)
            addView(errorViewBinding.root)
        }
    }

    fun setStateData() = changeState(State.DATA)

    fun setStateError() {
        changeState(State.ERROR)
    }

    fun setStateLoading() = changeState(State.LOADING)

    fun setOnRetryButtonClick(onClick: OnClickListener) {
        errorViewBinding.buttonStateFlipperRetry.setOnClickListener(onClick)
    }

    private fun changeState(newState: State) {
        if (currentState != newState || displayedChild != newState.displayedChild) {
            currentState = newState
            displayedChild = newState.displayedChild
        }
    }
}
