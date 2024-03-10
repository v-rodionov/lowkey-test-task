package com.rvv.android.test.taks.lowkey.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.rvv.android.test.taks.lowkey.core.Dependencies
import com.rvv.android.test.taks.lowkey.databinding.FragmentListOfPhotosBinding
import com.rvv.android.test.taks.lowkey.ui.base.observeCommonEvents
import com.rvv.android.test.taks.lowkey.ui.common.pagination.PagingLoadStateAdapter
import com.rvv.android.test.taks.lowkey.utils.addLinearSpaceItemDecoration
import com.rvv.android.test.taks.lowkey.utils.launchAndCollectIn

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListOfPhotosFragment : Fragment() {

    private var _binding: FragmentListOfPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListOfPhotosViewModel by viewModels { Dependencies.UiScope.getListOfPhotosViewModelFactory() }
    private val pageAdapter = ListOfPhotosAdapter(
        onClick = { item -> viewModel.onItemClick(item) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListOfPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        with(recyclerViewListOfPhotos) {
            setHasFixedSize(true)
            addLinearSpaceItemDecoration(dp = 8)
            adapter = pageAdapter.withLoadStateFooter(PagingLoadStateAdapter { pageAdapter.retry() })
        }

        swipeRefreshLayoutListOfPhotos.setOnRefreshListener { pageAdapter.refresh() }
        stateViewFlipperListOfPhotos.setOnRetryButtonClick { pageAdapter.retry() }

        pageAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Loading -> {
                    if (!swipeRefreshLayoutListOfPhotos.isRefreshing) {
                        swipeRefreshLayoutListOfPhotos.isEnabled = false
                        stateViewFlipperListOfPhotos.setStateLoading()
                    }
                }
                is LoadState.NotLoading -> {
                    swipeRefreshLayoutListOfPhotos.isRefreshing = false
                    swipeRefreshLayoutListOfPhotos.isEnabled = true
                    stateViewFlipperListOfPhotos.setStateData()
                }
                is LoadState.Error -> {
                    swipeRefreshLayoutListOfPhotos.isEnabled = false
                    stateViewFlipperListOfPhotos.setStateError()
                }
            }
        }

        viewModel.pagingData.launchAndCollectIn(viewLifecycleOwner) { data ->
            pageAdapter.submitData(viewLifecycleOwner.lifecycle, data)
        }

        viewModel.observeCommonEvents(fragment = this@ListOfPhotosFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
