package com.astraleratech.priviobrowser.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.astraleratech.priviobrowser.R
import com.astraleratech.priviobrowser.databinding.DailougeLayoutAddnewsavepageBinding

open class MyCutomBlurredDailouge(private val mActivity: Activity): Dialog(mActivity)  {
     private var dimmingOverlay: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)


        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) applyBlurAndDimEffect()

    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    private fun applyBlurAndDimEffect() {
        // Apply blur effect on the root view of the activity
        val rootView: View = mActivity.getWindow().getDecorView().getRootView()
        val blurEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
        rootView.setRenderEffect(blurEffect)

        // Create a dimming overlay view and add it to the root view
        dimmingOverlay = View(mActivity)
        dimmingOverlay!!.setBackgroundColor(Color.parseColor(mActivity.getResources().getString(R.string.renderEffect_createBlurEffect_color))) // 50% opacity black
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        mActivity.addContentView(dimmingOverlay, params)
    }

    private fun removeBlurAndDimEffect() {
        // Remove blur effect
        val rootView: View = mActivity.getWindow().getDecorView().getRootView()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            rootView.setRenderEffect(null)
        }

        // Remove the dimming overlay
        if (dimmingOverlay != null) {
            val parent = dimmingOverlay!!.getParent() as ViewGroup
            parent?.removeView(dimmingOverlay)
        }
    }

    override fun dismiss() {
        // Remove blur and dim effect when dialog is dismissed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            removeBlurAndDimEffect()
        }
        super.dismiss()
    }
}