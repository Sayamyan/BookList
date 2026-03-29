package com.coding.booklist.book.presentation.book_list

import com.coding.booklist.book.domain.Book
import com.coding.booklist.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Harry Potter",
    val searchResults: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: UiText? = null
)
