package com.rvv.android.test.taks.lowkey.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rvv.android.test.taks.lowkey.R
import com.rvv.android.test.taks.lowkey.databinding.ItemListOfPhotosBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation

class ListOfPhotosAdapter(
    private val onClick: (ListOfPhotosItem) -> Unit,
) : PagingDataAdapter<ListOfPhotosItem, ListOfPhotosItemViewHolder>(ListOfPhotosItemDiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfPhotosItemViewHolder {
        val binding = ItemListOfPhotosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListOfPhotosItemViewHolder(
            binding = binding,
            onClick = { position -> onClick(requireItem(position)) }
        )
    }

    override fun onBindViewHolder(holder: ListOfPhotosItemViewHolder, position: Int) {
        holder.bind(requireItem(position))
    }

    private fun requireItem(position: Int): ListOfPhotosItem {
        return requireNotNull(getItem(position)) { "placeholders are not supported " }
    }
}

class ListOfPhotosItemViewHolder(
    private val binding: ItemListOfPhotosBinding,
    private val onClick: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListOfPhotosItem) = with(binding) {
        root.setOnClickListener { onClick(bindingAdapterPosition) }
        textViewItemListOfPhotosAuthorName.text = item.author

        Picasso.get()
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_image_search_placeholder_24)
            .error(R.drawable.ic_image_search_placeholder_24)
            .transform(VignetteFilterTransformation(root.context))
            .into(imageViewItemListOfPhotosPhoto)
    }
}

class ListOfPhotosItemDiffUtilItemCallback : DiffUtil.ItemCallback<ListOfPhotosItem>() {

    override fun areItemsTheSame(oldItem: ListOfPhotosItem, newItem: ListOfPhotosItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListOfPhotosItem, newItem: ListOfPhotosItem): Boolean {
        return oldItem == newItem
    }
}
