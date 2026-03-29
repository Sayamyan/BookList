package com.coding.booklist.book.domain
import com.coding.booklist.core.domain.Result
import com.coding.booklist.core.domain.DataError

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
}