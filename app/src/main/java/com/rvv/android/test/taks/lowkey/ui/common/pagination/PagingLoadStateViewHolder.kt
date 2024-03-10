package com.rvv.android.test.taks.lowkey.ui.common.pagination

import androidx.core.view.isInvisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.rvv.android.test.taks.lowkey.databinding.ItemPagingStateBinding

class PagingLoadStateViewHolder(
    private val binding: ItemPagingStateBinding,
    retry: () -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.buttonItemPagingStateRetry.setOnClickListener { retry() }
    }

    fun bind(loadState: LoadState) = with(binding) {
        progressBarItemPagingState.isInvisible = loadState !is LoadState.Loading
        buttonItemPagingStateRetry.isInvisible = loadState !is LoadState.Error
        textViewItemPagingStateErrorMessage.isInvisible = loadState !is LoadState.Error
    }
}
