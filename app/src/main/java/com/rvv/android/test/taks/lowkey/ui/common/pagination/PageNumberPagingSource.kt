package com.rvv.android.test.taks.lowkey.ui.common.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState

private const val FIRST_PAGE_NUMBER = 1
private const val DEFAULT_LIMIT = 16

data class Page<T>(
    val items: List<T>,
    val pageNumber: Int,
    val hasNext: Boolean,
)

class PageNumberPagingSource<T : Any>(
    private val request: suspend (pageNumber: Int, limit: Int) -> Page<T>,
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val pageNumber = params.key ?: FIRST_PAGE_NUMBER
        val limit = params.loadSize
        return try {
            val result = request(pageNumber, limit)
            LoadResult.Page(
                data = result.items,
                prevKey = if (pageNumber == FIRST_PAGE_NUMBER) null else pageNumber - 1,
                nextKey = if (result.hasNext) pageNumber + 1 else null
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }
}

fun <T : Any> createPager(
    pageSize: Int = DEFAULT_LIMIT,
    pagingSourceFactory: () -> PagingSource<Int, T>,
): Pager<Int, T> {
    return Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false,
            initialLoadSize = pageSize,
            prefetchDistance = pageSize / 2
        ),
        pagingSourceFactory = pagingSourceFactory
    )
}
