package com.astraleratech.priviobrowser.features.homePage

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.astraleratech.priviobrowser.R
import com.astraleratech.priviobrowser.Ui.dailouges.AddNewSavePageDailouge
import com.astraleratech.priviobrowser.databinding.FragmentHomeBinding
import com.astraleratech.priviobrowser.features.browserPage.BrowserFragment
import com.astraleratech.priviobrowser.features.homeSavedPages.SavedPagesAdapter
import com.astraleratech.priviobrowser.features.homeTrendingSearches.TrendingSearchesAdapter
import com.astraleratech.priviobrowser.features.homeTrendingSearches.TrendingSearchesCallBack
import com.astraleratech.priviobrowser.features.homeTrendingSearches.TrendingSearchesDM
import com.astraleratech.priviobrowser.features.wheather.WeatherViewModel
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPage
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPageViewModel
import com.astraleratech.priviobrowser.utils.FragmentNavigator
import com.astraleratech.priviobrowser.utils.GoogleSearchHelper
import com.astraleratech.priviobrowser.utils.PrivioToast
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), SavedPagesAdapter.OnSavedPageCallBack, TrendingSearchesCallBack {
    var TAG = "dfads8Uu"

    @Inject
    lateinit var privioToast: PrivioToast
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val savedPageViewModel: SavedPageViewModel by viewModels()
    lateinit var binding: FragmentHomeBinding
    lateinit var savedPagesAdapter: SavedPagesAdapter
    var savedPagesArratList: ArrayList<SavedPage> = arrayListOf()
    lateinit var mContext: Context
    private lateinit var fragmentNavigator: FragmentNavigator

    override fun onAttach(context: Context) {
        this.mContext = context
        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        weatherViewModel.weatherData.observe(viewLifecycleOwner) { weatherResponse ->
            val tempInCelsius = weatherResponse.main.temp - 273.15
            binding.heading.text = String.format("%.2f°C", tempInCelsius)
            val imgURL = "https://openweathermap.org/img/wn/${weatherResponse.weather.get(0).icon}@4x.png"
            Glide.with(mContext).load(imgURL).into(binding.ivTemp)
        }
        weatherViewModel.fetchWeatherData(44.34, 10.99)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        initializer()
        initsavedPagesRV()
        getSavedPagesData()
        openClickListeners()
        initTrendingSearchesRV()
    }

    private fun initTrendingSearchesRV() {
        val flexboxLayoutManager = FlexboxLayoutManager(mContext)
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP

        binding.rvTrendingSearches.layoutManager = flexboxLayoutManager

        val trendingSearchesList = listOf(
            TrendingSearchesDM("Kargil Vijay Diwas"),
            TrendingSearchesDM("SSC CGL Admit Card"),
            TrendingSearchesDM("T20 2021"),
            TrendingSearchesDM("Cricket"),
            TrendingSearchesDM("Virat Kohli"),
            TrendingSearchesDM("Pro Kabaddi"),
            TrendingSearchesDM("Snapdeal")
        )
        binding.rvTrendingSearches.adapter = TrendingSearchesAdapter(trendingSearchesList, mContext, this)
    }

    private fun initializer() {

    }

    private fun initsavedPagesRV() {
        val layoutManager = StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
        binding.rvSavedPages.setLayoutManager(layoutManager)
        savedPagesAdapter = SavedPagesAdapter(savedPagesArratList, mContext, this)
        binding.rvSavedPages.setAdapter(savedPagesAdapter)

    }

    private fun openClickListeners() {
        binding.apply {
            cardEdit.setOnClickListener {
                savedPagesArratList.removeAt(savedPagesArratList.size - 1)
                savedPagesAdapter.setEditMode(true)
                savedPagesAdapter.notifyDataSetChanged()
                btnDoneEditing.visibility = View.VISIBLE
                cardEdit.visibility = View.INVISIBLE
            }
            btnDoneEditing.setOnClickListener {
                savedPagesAdapter.setEditMode(false)
                savedPagesAdapter.notifyDataSetChanged()
                btnDoneEditing.visibility = View.GONE
                cardEdit.visibility = View.VISIBLE
                savedPagesArratList.add(SavedPage(99999099, "", "", ""))
                //  getSavedPagesData()
            }
        }
    }

    private fun getSavedPagesData() {
        savedPagesArratList.clear()
        savedPageViewModel.getAllSavedPagesList { savedPages: List<SavedPage> ->
            savedPagesArratList.addAll(savedPages)
            if (!savedPagesAdapter.getEditMode()) {
                savedPagesArratList.add(SavedPage(99999099, "", "", ""))
            }
            savedPagesAdapter.notifyDataSetChanged()
        }
    }

    override fun onPageClicked(item: SavedPage) {
        BrowserFragment.setURL( item.pageUrl)
        fragmentNavigator.transitBrowserFragment()

    }

    override fun onEditClicked(item: SavedPage) {
        showAddNewPageDialog(isEditMode = true, item = item)

    }

    override fun onAddNewPage() {
        showAddNewPageDialog(isEditMode = false)
    }

    private fun showAddNewPageDialog(isEditMode: Boolean, item: SavedPage? = null) {
        val dialogConfirmation = activity?.let {
            AddNewSavePageDailouge(it, isEditMode, item, object : AddNewSavePageDailouge.DailougeDismiss {
                override fun onAddNewPage(item: SavedPage, isEditMode: Boolean) {
                    Log.d(TAG, "showAddNewPageDialog item: $item")
                    if (isEditMode) {
                        savedPageViewModel.updatePageById(item) {
                            getSavedPagesData()
                        }
                    } else {
                        savedPageViewModel.saveNewPage(item) {
                            getSavedPagesData()
                        }
                    }

                }

                override fun onDeletePage(item: SavedPage) {
                    savedPageViewModel.removePageById(item.pageID) {
                        getSavedPagesData()
                    }
                }
            })
        }

        dialogConfirmation?.apply {
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
                ?.getChildAt(0)
                ?.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.zoom_in))
            show()
        }
    }

    override fun onSeacrhItemClick(searchText: String) {
        BrowserFragment.setURL( GoogleSearchHelper.getGoogleSearchUrl(searchText))
        fragmentNavigator.transitBrowserFragment()
    }

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance(): HomeFragment {
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance!!
        }
    }

}