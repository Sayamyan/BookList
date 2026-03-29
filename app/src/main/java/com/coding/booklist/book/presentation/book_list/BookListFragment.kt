package com.coding.booklist.book.presentation.book_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.coding.booklist.R
import com.coding.booklist.book.presentation.SelectedBookViewModel
import com.coding.booklist.book.presentation.collectLatestLifecycleFlow
import com.coding.booklist.book.presentation.hideKeyboard
import com.coding.booklist.book.presentation.viewBinding
import com.coding.booklist.databinding.FragmentBookListBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue


class BookListFragment : Fragment(R.layout.fragment_book_list) {
    private val binding by viewBinding(FragmentBookListBinding::bind)
    private val viewModel: BookListViewModel by viewModel()
    private val selectedBookViewModel: SelectedBookViewModel by activityViewModel()
    private val adapter = BookAdapter { book ->
        selectedBookViewModel.onSelectBook(book)
        findNavController().navigate(R.id.action_list_to_details)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearch()
        handleState()
    }

    private fun handleState() {
        binding.bookRv.adapter = adapter
        collectLatestLifecycleFlow(viewModel.state) { state ->
            binding.loadingProgress.isVisible = if (state.isLoading) {
                true
            } else {
                when {
                    state.errorMessage != null -> {
                        binding.errorContainer.isVisible = true
                        val errorText = state.errorMessage.asString(requireContext())
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = errorText
                        binding.errorTv.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_error
                            )
                        )
                    }

                    state.searchResults.isEmpty() -> {
                        binding.errorContainer.isVisible = true
                        val emptyText = getString(R.string.no_search_results)
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = emptyText
                        binding.errorTv.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.red_error
                            )
                        )
                    }

                    else -> {
                        binding.errorContainer.isVisible = false
                        binding.bookRv.isVisible = true
                        adapter.submitList(state.searchResults)
                    }
                }
                false
            }
        }
    }

    private fun initSearch() {
        binding.searchEditText.setText(viewModel.state.value.searchQuery)
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onQueryChanged(text.toString())
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                true
            } else false
        }
    }
}