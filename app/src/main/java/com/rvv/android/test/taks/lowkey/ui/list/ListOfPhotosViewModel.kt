package com.rvv.android.test.taks.lowkey.ui.list

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.rvv.android.test.taks.lowkey.data.ContentRepository
import com.rvv.android.test.taks.lowkey.data.Src
import com.rvv.android.test.taks.lowkey.ui.base.BaseViewModel
import com.rvv.android.test.taks.lowkey.ui.base.pagination.Page
import com.rvv.android.test.taks.lowkey.ui.base.pagination.PageNumberPagingSource
import com.rvv.android.test.taks.lowkey.ui.base.pagination.createPager
import com.rvv.android.test.taks.lowkey.ui.details.PhotoDetails
import com.rvv.android.test.taks.lowkey.ui.details.PhotoDetailsArgs

class ListOfPhotosViewModel(
    private val contentRepository: ContentRepository,
) : BaseViewModel() {

    val pagingData = createPager {
        PageNumberPagingSource { pageNumber, limit ->
            val data = contentRepository.getCurated(pageNumber, limit)
            Page(
                items = data.photos.map { photo ->
                    ListOfPhotosItem(photo.id, photo.photographer, findBestPhotoUrl(photo.src))
                },
                pageNumber = pageNumber,
                hasNext = !data.nextPage.isNullOrBlank()
            )
        }
    }.flow.cachedIn(viewModelScope)

    fun onItemClick(item: ListOfPhotosItem) {
        val args = PhotoDetailsArgs(PhotoDetails(item.id, item.imageUrl, item.author))
        forward(ListOfPhotosFragmentDirections.actionListOfPhotosToPhotoDetails(args))
    }

    // TODO v_roiodnov:
    private fun findBestPhotoUrl(src: Src): String {
        return src.medium
    }
}
