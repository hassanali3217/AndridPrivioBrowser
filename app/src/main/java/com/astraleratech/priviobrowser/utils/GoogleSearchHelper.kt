package com.astraleratech.priviobrowser.utils

object GoogleSearchHelper {

    fun getGoogleSearchUrl(searchText: String): String {
        // Replace spaces with "+" for URL encoding of search terms
        val encodedSearchText = searchText.trim().replace(" ", "+")
        return "https://www.google.com/search?q=$encodedSearchText"
    }
}