package com.astraleratech.priviobrowser.features.browserPage

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.astraleratech.priviobrowser.databinding.FragmentBrowserBinding
import com.astraleratech.priviobrowser.utils.FragmentNavigator
import com.astraleratech.priviobrowser.utils.NavigationViewModel
import com.astraleratech.priviobrowser.utils.PrivioToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BrowserFragment : Fragment() {


    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private val browserViewModel: BrowserViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by activityViewModels()
    lateinit var binding: FragmentBrowserBinding
    lateinit var mContext: Context
    private lateinit var fragmentNavigator: FragmentNavigator

    @Inject
    lateinit var privioToast: PrivioToast

    override fun onAttach(context: Context) {
        this.mContext = context
        if (context is FragmentNavigator) {
            fragmentNavigator = context
        }
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBrowserBinding.inflate(layoutInflater)
//        url = arguments?.getString("url_key")
        webView = binding.webView
        progressBar = binding.progressBar
        setupWebView()
        observeNavigationCommands()
        startClickListeners()
        // Observe the URL from the ViewModel
        browserViewModel.url.observe(viewLifecycleOwner) { url ->
            webView.loadUrl(url)
        }

        privioToast.showToast(url.toString())
        browserViewModel.loadUrl(url.toString())

        return binding.root
    }

    private fun startClickListeners() {
        binding.ivHome.setOnClickListener {
            fragmentNavigator.transitFragment("home")
        }
    }

    private fun setupWebView() {
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                binding.etSearchText.setText(webView.url.toString())
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
    }


    private fun observeNavigationCommands() {
        navigationViewModel.navigationCommand.observe(viewLifecycleOwner) { command ->
            when (command) {
                NavigationViewModel.NavigationCommand.GO_BACK -> {
                    privioToast.showToast("backed")
                    if (webView.canGoBack()) {
                        webView.goBack()
                    }
                }
                NavigationViewModel.NavigationCommand.GO_FORWARD -> {
                    privioToast.showToast("reversed")
                    if (webView.canGoForward()) {
                        webView.goForward()

                    }
                }
            }
        }
    }

    companion object {
        private var instance: BrowserFragment? = null
        var url: String? = ""
        var TAG: String? = "asfa9sms"
        fun setURL(url: String) {
            Log.d(TAG, "get url ${url}")
            this.url = url;
        }

        fun getInstance(): BrowserFragment {
            if (instance == null) {
                instance = BrowserFragment()
            }
            return instance!!
        }
    }

}