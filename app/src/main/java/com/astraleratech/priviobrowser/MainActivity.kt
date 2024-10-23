package com.astraleratech.priviobrowser

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.astraleratech.priviobrowser.databinding.ActivityMainBinding
import com.astraleratech.priviobrowser.utils.FragmentNavigator
import com.astraleratech.priviobrowser.utils.HomeFragmentFactory
import com.astraleratech.priviobrowser.utils.NavigationViewModel
import com.astraleratech.priviobrowser.utils.PrivioSharedPref
import com.astraleratech.priviobrowser.utils.PrivioToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentNavigator {
    lateinit var binding: ActivityMainBinding
    var fragment: Fragment? = null
    lateinit var mContext: Context
    private val navigationViewModel: NavigationViewModel by viewModels()
    private val TAG = "main09_0"

    @Inject
    lateinit var privioSharedPref: PrivioSharedPref

    @Inject
    lateinit var privioToast: PrivioToast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializer()
        setBottomNavBarListeners()
    }

    private fun initializer() {
        mContext = this
        binding.bottomBar.selectedItemId = R.id.action_home
        transitFragment("home")
    }

    private fun setBottomNavBarListeners() {
        binding.apply {

            bottomBar.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_back -> {
                        navigationViewModel.setNavigationCommand(NavigationViewModel.NavigationCommand.GO_BACK)
                        false
                    }
                    R.id.action_forward -> {
                        navigationViewModel.setNavigationCommand(NavigationViewModel.NavigationCommand.GO_FORWARD)
                        false
                    }
                    R.id.action_home -> {
                        transitFragment("home")
                        true
                    }
                    R.id.action_setting -> {
                        transitFragment("setting")
                        true
                    }
                    R.id.action_tab -> {
                        transitFragment("setting")
                        true
                    }
                    else -> false
                }
            }

        }
    }

    override fun transitFragment(fragmentTag: String) {
        if (fragmentTag.contains("home") || fragmentTag.contains("browse")){
            privioSharedPref.setLastTransactedTabTag(fragmentTag)
            Log.d(TAG, "last tarasvedFrag = ${fragmentTag}")
        }
        val fragment = HomeFragmentFactory.getFragment(fragmentTag) // or HomeFragment.getInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_frame, fragment).addToBackStack(null)
        transaction.commit()
    }

    override fun transitHomeFragment() {
        binding.bottomBar.selectedItemId = R.id.action_home
        transitFragment("home")
    }

    override fun transitSettingFragment() {
        binding.bottomBar.selectedItemId = R.id.action_setting
        transitFragment("setting")
    }

    override fun transitBrowserFragment() {
        // binding.bottomBar.selectedItemId = R.id.action_browser
        transitFragment("browser")
    }


}