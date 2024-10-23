package com.astraleratech.priviobrowser.privioDB.savedPages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedPageViewModel @Inject constructor(private val repository: SavedPageRepository) : ViewModel() {
    init {
        // Call the method to insert initial rows
        viewModelScope.launch {
            repository.insertInitialSavedPagesIfEmpty()
        }
    }

    fun saveNewPage(pageName: String, pageUrl: String, favIcon: String) {
        val newPage = SavedPage(pageName = pageName, pageUrl = pageUrl, favIconUrl = favIcon)
        viewModelScope.launch {
            repository.saveNewPage(newPage)
        }
    }

    fun saveNewPage(newPage: SavedPage, callback: () -> Unit) {
        viewModelScope.launch {
            repository.saveNewPage(newPage)
            callback()
        }
    }

    fun getPageByName(name: String, callback: (SavedPage?) -> Unit) {
        viewModelScope.launch {
            val page = repository.getPageByName(name)
            callback(page)
        }
    }

    fun removePageById(id: Long, callback: () -> Unit) {
        viewModelScope.launch {
            repository.removePageById(id)  // Remove the page by its ID
            callback()  // Trigger the callback to indicate that the operation is complete
        }
    }

    fun getAllSavedPagesList(callback: (List<SavedPage>) -> Unit) {
        viewModelScope.launch {
            val pages = repository.getAllSavedPagesList()
            callback(pages)
        }
    }

    fun updatePageById(savedPage: SavedPage, callback: () -> Unit) {
        viewModelScope.launch {
            repository.updatePageById(savedPage)
            callback()
        }
    }
}
