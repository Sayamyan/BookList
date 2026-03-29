package com.coding.booklist.book.presentation.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.booklist.book.domain.Book
import com.coding.booklist.book.domain.BookRepository
import com.coding.booklist.core.domain.onError
import com.coding.booklist.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: BookRepository
): ViewModel() {
    var bookId: String = ""
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()
        .onStart {
            fetchBookDescription()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun setSelectedBook(book: Book?) {
        _state.update {
            it.copy(book = book)
        }
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = description
                            ),
                            isLoading = false
                        )
                    }
                }
                .onError {
                    println()
                }
        }
    }
}