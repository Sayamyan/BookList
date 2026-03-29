package com.coding.booklist.book.data.network

import com.coding.booklist.book.data.dto.BookWorkDto
import com.coding.booklist.book.data.dto.SearchResponseDto
import com.coding.booklist.core.domain.DataError
import com.coding.booklist.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>

}