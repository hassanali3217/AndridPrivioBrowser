package com.astraleratech.priviobrowser.privioDB.savedPages

import com.astraleratech.priviobrowser.R

class SavedPageRepository(private val savedPageDao: SavedPageDao) {

    // Function to insert initial rows
    suspend fun insertInitialSavedPagesIfEmpty() {
        val count = savedPageDao.getCount()
        if (count == 0) {
            val initialSavedPages = listOf(
                SavedPage(pageName = "Facebook", pageUrl = "https://www.facebook.com", favIconResId = R.drawable.img_facebook , isPreDefine = true , favIconUrl = ""),
                SavedPage(pageName = "Instagram", pageUrl = "https://www.instagram.com", favIconResId = R.drawable.img_instagram , isPreDefine = true , favIconUrl = ""),
                SavedPage(pageName = "Twitter", pageUrl = "https://www.twitter.com", favIconResId = R.drawable.img_twitter , isPreDefine = true , favIconUrl = ""),
                SavedPage(pageName = "YouTube", pageUrl = "https://www.youtube.com", favIconResId = R.drawable.img_utube , isPreDefine = true , favIconUrl = ""),
            )

            savedPageDao.insertAll(initialSavedPages)
        }
    }

    suspend fun saveNewPage(savedPage: SavedPage) {
        savedPageDao.saveNewPage(savedPage)
    }

    suspend fun removePageById(id: Long) {
        savedPageDao.removePageById(id)
    }

    suspend fun getPageByName(name: String): SavedPage? {
        return savedPageDao.getPageByName(name)
    }

    suspend fun getAllSavedPagesList(): List<SavedPage> {
        return savedPageDao.getAllSavedPagesList()
    }

    suspend fun updatePageById(savedPage: SavedPage) {
        savedPageDao.updatePageById(savedPage)
    }
}
