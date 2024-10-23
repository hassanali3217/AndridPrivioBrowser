package com.astraleratech.priviobrowser.features.homeSavedPages

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.astraleratech.priviobrowser.R
import com.astraleratech.priviobrowser.databinding.RvItemSavedPageBinding
import com.astraleratech.priviobrowser.privioDB.savedPages.SavedPage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SavedPagesAdapter(var model: ArrayList<SavedPage>, var mContext: Context,
                        var onSavedPageCallBack: OnSavedPageCallBack) : RecyclerView.Adapter<SavedPagesAdapter.ViewHolder>() {

    private var isEditMode: Boolean = false
    var TAG = "dfads8Uu"

    // Method to enable or disable edit mode from the driver class
    fun setEditMode(enabled: Boolean) {
        isEditMode = enabled
        notifyDataSetChanged() // Refresh the adapter to apply changes to all items
    }

    fun getEditMode(): Boolean {
        Log.d(TAG, "isEditMode " + isEditMode);
        return isEditMode

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemSavedPageBinding.inflate(LayoutInflater.from(mContext), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item: SavedPage = model.get(position)
        if (item.pageID != 99999099L) {
            val options = RequestOptions()
                .placeholder(R.drawable.ic_globe)
                .fitCenter()
                .error(R.drawable.ic_globe)
            Glide.with(mContext).load(item.favIconUrl).apply(options).into(holder.binding.ivFavIcon)

            if (item.isPreDefine) {
                Glide.with(mContext).load(item.favIconResId).apply(options).into(holder.binding.ivFavIcon)

            }
            holder.binding.apply {
                if (isEditMode && !item.isPreDefine) {
                    startShakeAnimation(parent)
                    cnst.setOnClickListener {
                        onSavedPageCallBack.onEditClicked(item)
                    }
                } else {
                    cnst.setOnClickListener {
                        onSavedPageCallBack.onPageClicked(item)
                    }
                    parent.clearAnimation()
                }
                tvName.text = item.pageName

            }
        } else {
            //  Toast.makeText(mContext,getRandomColor(mContext).toString() , Toast.LENGTH_SHORT).show()
            Glide.with(mContext).load(R.drawable.ic_add).into(holder.binding.ivFavIcon)
            holder.binding.tvName.text = "Add Page"
            holder.binding.cnst.setOnClickListener {
                this.onSavedPageCallBack.onAddNewPage()
            }
        }

    }

    // Shake animation for edit mode
    private fun startShakeAnimation(view: View) {
        // List of shake animations to choose from
        val shakeAnimations = listOf(R.anim.shake, R.anim.shake2, R.anim.shake3)
        // Randomly pick one of the shake animations
        val randomShakeAnimation = shakeAnimations.random()
        // Load and start the selected shake animation
        val animation = AnimationUtils.loadAnimation(mContext, randomShakeAnimation)
        view.startAnimation(animation)
    }

    override fun getItemCount(): Int {
        return model.size
    }

    interface OnSavedPageCallBack {
        public fun onPageClicked(item: SavedPage)
        public fun onEditClicked(item: SavedPage)
        public fun onAddNewPage()
    }

    fun getRandomColor(context: Context): Int {
        val colorCodes = context.resources.getStringArray(R.array.array_saved_page_bg_colors)
        val randomColorCode = colorCodes.random()
        return Color.parseColor(randomColorCode)
    }

    class ViewHolder(var binding: RvItemSavedPageBinding) : RecyclerView.ViewHolder(binding.root)
}