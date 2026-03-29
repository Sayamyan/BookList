package com.coding.booklist.book.presentation.book_details

import com.coding.booklist.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val book: Book? = null
)