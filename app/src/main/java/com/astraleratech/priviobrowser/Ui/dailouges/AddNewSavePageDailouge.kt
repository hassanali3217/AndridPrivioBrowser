package com.astraleratech.priviobrowser.Ui.dailouges

import android.app.Activity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.Toast
import com.astraleratech.priviobrowser.databinding.DailougeLayoutAddnewsavepageBinding
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPage
import com.astraleratech.priviobrowser.utils.MyCutomBlurredDailouge
import kotlin.properties.Delegates
class AddNewSavePageDailouge(private var mActivity: Activity) : MyCutomBlurredDailouge(mActivity) {

    var isEditMode by Delegates.notNull<Boolean>()
    private var item: SavedPage? = null
    private lateinit var dailougeDismiss: DailougeDismiss
    private lateinit var binding: DailougeLayoutAddnewsavepageBinding

    // Constructor for edit mode
    constructor(
        mActivity: Activity,
        isEditMode: Boolean,
        item: SavedPage?,
        dailougeDismiss: DailougeDismiss
    ) : this(mActivity) {
        this.isEditMode = isEditMode
        this.item = item
        this.dailougeDismiss = dailougeDismiss
    }

    // Constructor for add mode (no item needed)
    constructor(
        mActivity: Activity,
        isEditMode: Boolean,
        dailougeDismiss: DailougeDismiss
    ) : this(mActivity) {
        this.isEditMode = isEditMode
        this.dailougeDismiss = dailougeDismiss
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DailougeLayoutAddnewsavepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openListeners()

        // Handle UI updates based on edit mode
        if (isEditMode && item != null) {
            binding.apply {
                tlName.editText?.setText(item?.pageName)
                tlUrl.editText?.setText(item?.pageUrl)
                btnDelete.visibility = View.VISIBLE
                btnAdd.text = "Update"
            }
        }
    }

    private fun openListeners() {
        binding.apply {
            btnAdd.setOnClickListener {
                if (isValidUrl(urlEt.text.toString())) {
                    if (!nameEt.text.toString().isEmpty()) {
                        dismiss()
                        createObjAndThrowBack(nameEt.text.toString(), urlEt.text.toString())
                    } else {
                        Toast.makeText(mActivity, "Please enter name", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(mActivity, "Invalid URL", Toast.LENGTH_SHORT).show()
                }
            }
            btnDelete.setOnClickListener {
                item?.let {
                    dailougeDismiss.onDeletePage(it)
                    dismiss()
                }
            }
            btnSkip.setOnClickListener {
                dismiss()
            }
            ivCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun createObjAndThrowBack(name: String, url: String) {
        val favIcon = "https://t0.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=$url&size=16"

        val newItem: SavedPage = if (!isEditMode) {
            SavedPage(pageName = name, pageUrl = url, favIconUrl = favIcon)
        } else {
            SavedPage(pageID = item?.pageID ?: 0, pageName = name, pageUrl = url, favIconUrl = favIcon)
        }
        dailougeDismiss.onAddNewPage(newItem, isEditMode)
    }

    interface DailougeDismiss {
        fun onAddNewPage(item: SavedPage, isEditMode: Boolean)
        fun onDeletePage(item: SavedPage)
    }

    fun isValidUrl(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }
}
