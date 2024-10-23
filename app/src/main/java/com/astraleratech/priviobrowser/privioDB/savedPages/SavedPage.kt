package com.astraleratech.priviobrowser.privioDB.savedPages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblSavedPages" )
data class SavedPage(
    @PrimaryKey(autoGenerate = true) val pageID: Long = 0,
    val pageName: String,
    val pageUrl: String,
    val favIconUrl: String,
    val favIconResId: Int = 0,
    val isPreDefine: Boolean = false
)
