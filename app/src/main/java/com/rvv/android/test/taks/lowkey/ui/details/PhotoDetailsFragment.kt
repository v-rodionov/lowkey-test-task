package com.rvv.android.test.taks.lowkey.ui.details

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.rvv.android.test.taks.lowkey.core.Dependencies
import com.rvv.android.test.taks.lowkey.databinding.FragmentPhotoDetailsBinding
import com.rvv.android.test.taks.lowkey.ui.base.observeCommonEvents
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoDetailsArgs(val photoDetails: PhotoDetails) : Parcelable

class PhotoDetailsFragment : Fragment() {

    private var _binding: FragmentPhotoDetailsBinding? = null
    private val binding get() = _binding!!
    private val navArgs: PhotoDetailsFragmentArgs by navArgs()
    private val viewModel: PhotoDetailsViewModel by viewModels {
        Dependencies.UiScope.getPhotoDetailsViewModelFactory(navArgs.args)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPhotoDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeCommonEvents(fragment = this@PhotoDetailsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}