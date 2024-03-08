package com.rvv.android.test.taks.lowkey.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.rvv.android.test.taks.lowkey.core.DI
import com.rvv.android.test.taks.lowkey.databinding.FragmentListOfPhotosBinding
import com.rvv.android.test.taks.lowkey.ui.base.observeCommonEvents
import com.rvv.android.test.taks.lowkey.ui.base.pagination.PagingLoadStateAdapter
import com.rvv.android.test.taks.lowkey.utils.launchAndCollectIn

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListOfPhotosFragment : Fragment() {

    private var _binding: FragmentListOfPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListOfPhotosViewModel by viewModels { DI.UiScope.listOfPhotosViewModelFactory }
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
            adapter = pageAdapter.withLoadStateFooter(PagingLoadStateAdapter { pageAdapter.retry() })
        }

        stateViewFlipperListOfPhotos.setOnRetryButtonClick { pageAdapter.retry() }

        pageAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Loading -> stateViewFlipperListOfPhotos.setStateLoading()
                is LoadState.NotLoading -> stateViewFlipperListOfPhotos.setStateData()
                is LoadState.Error -> stateViewFlipperListOfPhotos.setStateError()
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
