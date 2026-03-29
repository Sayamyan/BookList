package com.coding.booklist.book.data.repository

import com.coding.booklist.book.data.mappers.toBook
import com.coding.booklist.book.domain.Book
import com.coding.booklist.core.domain.DataError
import com.coding.booklist.book.data.network.RemoteBookDataSource
import com.coding.booklist.book.domain.BookRepository
import com.coding.booklist.core.domain.map
import com.coding.booklist.core.domain.Result

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
) : BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        return  remoteBookDataSource.getBookDetails(bookId).map { it.description }
    }
}
