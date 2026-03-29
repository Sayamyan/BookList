package com.coding.booklist.book.presentation.book_details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.coding.booklist.R
import com.coding.booklist.book.presentation.SelectedBookViewModel
import com.coding.booklist.book.presentation.collectLatestLifecycleFlow
import com.coding.booklist.book.presentation.viewBinding
import com.coding.booklist.databinding.FragmentBookDetailsBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.bumptech.glide.Glide
import jp.wasabeef.glide.transformations.BlurTransformation

class BookDetailsFragment : Fragment(R.layout.fragment_book_details) {
    private val binding by viewBinding(FragmentBookDetailsBinding::bind)
    private val viewModel: BookDetailsViewModel by viewModel()
    private val selectedBookViewModel: SelectedBookViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backIv.setOnClickListener {
            findNavController().navigateUp()
        }
        setBookDetails()
    }

    private fun setBookDetails() {
        val book = selectedBookViewModel.selectedBook.value
        viewModel.bookId = book?.id.orEmpty()
        viewModel.setSelectedBook(book)

        if (book?.imageUrl.isNullOrEmpty() || book.imageUrl.contains("null")) {
            binding.blurIv.setBackgroundColor(requireContext().getColor(R.color.light_gray))
            binding.bookIv.setImageResource(R.drawable.ic_book_error)
        } else {
            Glide.with(binding.blurIv.context)
                .load(book.imageUrl)
                .transform(BlurTransformation(25, 3)) // radius, sampling
                .into(binding.blurIv)
            Glide.with(binding.bookIv.context)
                .load(book.imageUrl)
                .into(binding.bookIv)
        }

        binding.titleTv.text = book?.title
        binding.authorsTv.text = book?.authors?.joinToString()
        collectLatestLifecycleFlow(viewModel.state) { state ->
            if (!state.book?.description.isNullOrEmpty()) {
                binding.synopsisTv.isVisible = true
                binding.descriptionTv.text = state.book.description
            }
        }
    }
}