package com.astraleratech.priviobrowser.dependencyInjc

import android.content.Context
import com.astraleratech.priviobrowser.features.wheather.WeatherRepository
import com.astraleratech.priviobrowser.privioDB.PrivioDb
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPageDao
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPageRepository
import com.astraleratech.priviobrowser.utils.PrivioSharedPref
import com.astraleratech.priviobrowser.utils.PrivioToast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModuleSingleton {

    @Provides
    @Singleton
    fun providePrivioDb(@ApplicationContext context: Context): PrivioDb {
        return PrivioDb.getDatabase(context)
    }

    @Provides
    fun provideSavedPageDao(privioDb: PrivioDb): SavedPageDao {
        return privioDb.savedPageDao()
    }

    @Provides
    fun provideSavedPageRepository(savedPageDao: SavedPageDao): SavedPageRepository {
        return SavedPageRepository(savedPageDao)
    }

    @Provides
    @Singleton
    fun providePrivioToast(@ApplicationContext context: Context): PrivioToast {
        return PrivioToast(context)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository {
        return WeatherRepository()
    }

    @Provides
    @Singleton
    fun providePrivioSharedPref(@ApplicationContext context: Context): PrivioSharedPref {
        return PrivioSharedPref(context)
    }
}
