package com.astraleratech.priviobrowser.privioDB.savedPages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SavedPageViewModelFactory(private val repository: SavedPageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavedPageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
