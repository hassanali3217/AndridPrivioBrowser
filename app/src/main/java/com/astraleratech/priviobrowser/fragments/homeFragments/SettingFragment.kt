package com.astraleratech.priviobrowser.fragments.homeFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.astraleratech.priviobrowser.R


class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs, container, false)
    }

    companion object {
        private var instance: SettingFragment? = null
        fun getInstance(): SettingFragment {
            if (instance == null) {
                instance = SettingFragment()
            }
            return instance!!
        }
    }
}