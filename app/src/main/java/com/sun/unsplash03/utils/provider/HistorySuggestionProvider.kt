package com.sun.unsplash03.utils.provider

import android.content.SearchRecentSuggestionsProvider

class HistorySuggestionProvider : SearchRecentSuggestionsProvider() {

    init {
        setupSuggestions(AUTHORITY, MODE)
    }

    companion object {
        const val AUTHORITY = "com.sun.unsplash03"
        const val MODE = DATABASE_MODE_QUERIES
    }
}
