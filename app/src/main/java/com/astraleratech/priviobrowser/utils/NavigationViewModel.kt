package com.astraleratech.priviobrowser.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {

    private val _navigationCommand = MutableLiveData<NavigationCommand>()
    val navigationCommand: LiveData<NavigationCommand> = _navigationCommand

    fun setNavigationCommand(command: NavigationCommand) {
        _navigationCommand.value = command
    }

    enum class NavigationCommand {
        GO_BACK, GO_FORWARD
    }
}
