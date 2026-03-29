package com.coding.booklist.core.presentation

import android.content.Context
import androidx.annotation.StringRes


sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    class StringResourceId(
        @StringRes val id: Int,
        val args: Array<Any> = emptyArray()
    ) : UiText

    // ✅ XML / View system usage
    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResourceId -> context.getString(id, *args)
        }
    }
}