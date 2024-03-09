package com.rvv.android.test.taks.lowkey.ui.base.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.rvv.android.test.taks.lowkey.databinding.ItemPagingStateBinding

class PagingLoadStateAdapter(
    private val onRetry: () -> Unit,
) : LoadStateAdapter<PagingLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PagingLoadStateViewHolder {
        val binding = ItemPagingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagingLoadStateViewHolder(binding, onRetry)
    }
}
