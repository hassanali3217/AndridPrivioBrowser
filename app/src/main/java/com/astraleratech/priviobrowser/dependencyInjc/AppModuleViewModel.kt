package com.astraleratech.priviobrowser.dependencyInjc

import com.astraleratech.priviobrowser.utils.NavigationViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModuleViewModel {

    @Provides
    @ViewModelScoped
    fun provideNavigationViewModel(): NavigationViewModel {
        return NavigationViewModel()
    }
}
