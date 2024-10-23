package com.astraleratech.priviobrowser.privioDB

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPage
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPageDao

@Database(entities = [SavedPage::class], version = 1)
abstract class PrivioDb : RoomDatabase() {

    abstract fun savedPageDao(): SavedPageDao

    companion object {
        @Volatile
        private var INSTANCE: PrivioDb? = null

        fun getDatabase(context: Context): PrivioDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrivioDb::class.java,
                    "privioDb"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
