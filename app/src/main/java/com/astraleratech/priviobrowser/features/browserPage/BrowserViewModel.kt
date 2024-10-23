package com.astraleratech.priviobrowser.features.browserPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BrowserViewModel : ViewModel() {
    private val _url = MutableLiveData<String>()
    val url: LiveData<String> get() = _url

    fun loadUrl(newUrl: String) {
        _url.value = newUrl
    }
}
