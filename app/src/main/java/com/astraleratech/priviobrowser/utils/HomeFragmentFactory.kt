package com.astraleratech.priviobrowser.utils

import androidx.fragment.app.Fragment
import com.astraleratech.priviobrowser.features.browserPage.BrowserFragment
import com.astraleratech.priviobrowser.features.homePage.HomeFragment
import com.astraleratech.priviobrowser.fragments.homeFragments.SettingFragment

object HomeFragmentFactory {
    private val fragmentCache = mutableMapOf<String, Fragment>()

    fun getFragment(tag: String ): Fragment {
        return fragmentCache.getOrPut(tag) {
            when (tag) {
                "home" -> HomeFragment.getInstance() // Make sure this method exists
                "setting" -> SettingFragment.getInstance() // New instance for a different fragment
                "browser" -> BrowserFragment.getInstance() // New instance for a different fragment

                else -> throw IllegalArgumentException("Unknown fragment tag: $tag")
            }
        }
    }
}