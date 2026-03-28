package com.coding.booklist.di

import com.coding.booklist.core.data.HttpClientFactory
import org.koin.dsl.module


val networkModule = module {
    single { HttpClientFactory.create(get()) }
}