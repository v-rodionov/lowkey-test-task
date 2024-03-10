package com.rvv.android.test.taks.lowkey.ui.details

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.piasy.biv.loader.ImageLoader
import com.rvv.android.test.taks.lowkey.core.Dependencies
import com.rvv.android.test.taks.lowkey.databinding.FragmentPhotoDetailsBinding
import com.rvv.android.test.taks.lowkey.ui.base.observeCommonEvents
import com.rvv.android.test.taks.lowkey.utils.launchAndCollectIn
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
@Keep
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

        toolbarPhotoDetails.setNavigationOnClickListener { findNavController().navigateUp() }

        stateViewFlipperPhotoDetails.setOnRetryButtonClick {
            val imageUrl = viewModel.imageUrl.value
            imageViewPhotoDetails.showImage(imageUrl.toUri())
        }

        imageViewPhotoDetails.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File?) = Unit
            override fun onCacheMiss(imageType: Int, image: File?) = Unit
            override fun onStart() = Unit
            override fun onFinish() = Unit

            override fun onProgress(progress: Int) {
                stateViewFlipperPhotoDetails.setStateLoading()
            }

            override fun onSuccess(image: File?) {
                stateViewFlipperPhotoDetails.setStateData()
            }

            override fun onFail(error: Exception?) {
                stateViewFlipperPhotoDetails.setStateError()
            }
        })

        viewModel.imageUrl.launchAndCollectIn(viewLifecycleOwner) { url ->
            imageViewPhotoDetails.showImage(url.toUri())
        }

        viewModel.observeCommonEvents(fragment = this@PhotoDetailsFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}