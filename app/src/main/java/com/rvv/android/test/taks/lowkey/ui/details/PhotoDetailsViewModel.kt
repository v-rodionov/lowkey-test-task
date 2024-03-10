package com.rvv.android.test.taks.lowkey.ui.details

import com.rvv.android.test.taks.lowkey.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PhotoDetailsViewModel(
    args: PhotoDetailsArgs,
) : BaseViewModel() {

    private val _imageUrl = MutableStateFlow(args.photoDetails.imageUrl)
    val imageUrl = _imageUrl.asStateFlow()
}