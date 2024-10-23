package com.astraleratech.priviobrowser.privioDB.savedPages

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete

@Dao
interface SavedPageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(savedPages: List<SavedPage>)

    @Query("SELECT COUNT(*) FROM tblSavedPages")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewPage(savedPage: SavedPage)

    @Query("DELETE FROM tblSavedPages WHERE pageID = :id")
    suspend fun removePageById(id: Long)

    @Query("SELECT * FROM tblSavedPages WHERE pageName = :name LIMIT 1")
    suspend fun getPageByName(name: String): SavedPage?

    @Query("SELECT * FROM tblSavedPages")
    suspend fun getAllSavedPagesList(): List<SavedPage>

    @Update
    suspend fun updatePageById(savedPage: SavedPage)
}
