package com.coding.booklist.book.presentation.book_list

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.coding.booklist.R
import com.coding.booklist.book.domain.Book
import com.coding.booklist.book.presentation.viewBinding
import com.coding.booklist.databinding.ItemBookBinding
import kotlin.math.round

class BookAdapter(
    private val onClick: (Book?) -> Unit
) : ListAdapter<Book, BookAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(a: Book, b: Book) = a.id == b.id
        override fun areContentsTheSame(a: Book, b: Book) = a == b
    }

    inner class ViewHolder(
        private val binding: ItemBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private var book: Book? = null

        init {
            binding.root.setOnClickListener {
                onClick(book)
            }
        }

        fun bind(item: Book?) {
            this.book = item
            binding.titleTv.text = item?.title
            book?.authors?.firstOrNull()?.let { authorName ->
                binding.authorTv.text = authorName
            }
            book?.averageRating?.let { rating ->
                binding.ratingTv.text = "${round(rating * 10) / 10.0}"
                binding.starIv.isVisible = true
            }
            Glide.with(binding.bookImageIv.context)
                .load(item?.imageUrl)
                .error(R.drawable.ic_book_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progress.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable?>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progress.isVisible = false
                        return false
                    }
                })
                .into(binding.bookImageIv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = parent.viewBinding(ItemBookBinding::inflate)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
