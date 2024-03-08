package com.rvv.android.test.taks.lowkey.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * To avoid something like:
 *
 *     private val _livaData = MutableLiveData<Boolean>()
 *     val liveData: LiveData<Boolean> = _livaData
 *
 *     ...
 *
 *     _livaData.value = true
 */
fun <T> LiveData<T>.setData(data: T) {
    if (this is MutableLiveData<*>) {
        value = data
    }
}

/**
 * To avoid something like:
 *
 *     private val _livaData = MutableLiveData<Boolean>()
 *     val liveData: LiveData<Boolean> = _livaData
 *
 *     ...
 *
 *     _livaData.postValue(true)
 */
fun <T> LiveData<T>.postData(data: T) {
    if (this is MutableLiveData<T>) {
        postValue(data)
    }
}
