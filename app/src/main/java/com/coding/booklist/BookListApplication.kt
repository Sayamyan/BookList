package com.coding.booklist

import android.app.Application
import com.coding.booklist.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookListApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BookListApplication)
        }
    }
}