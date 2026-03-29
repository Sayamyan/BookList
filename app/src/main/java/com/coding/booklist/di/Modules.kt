package com.coding.booklist.di

import com.coding.booklist.book.data.network.KtorRemoteBookDataSource
import com.coding.booklist.book.data.network.RemoteBookDataSource
import com.coding.booklist.book.data.repository.DefaultBookRepository
import com.coding.booklist.book.domain.BookRepository
import com.coding.booklist.book.presentation.SelectedBookViewModel
import com.coding.booklist.book.presentation.book_details.BookDetailsViewModel
import com.coding.booklist.book.presentation.book_list.BookListViewModel
import com.coding.booklist.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val networkModule = module {
    single<HttpClientEngine> { Android.create() }
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()
}

val appModule = module {
    viewModelOf (::BookListViewModel )
    viewModelOf (::SelectedBookViewModel )
    viewModelOf (::BookDetailsViewModel )

}