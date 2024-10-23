package com.astraleratech.priviobrowser.features.homeTrendingSearches

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astraleratech.priviobrowser.R
import com.astraleratech.priviobrowser.databinding.RvItemTerndingSearchesBinding

class TrendingSearchesAdapter(private val tags: List<TrendingSearchesDM>, private val mContext: Context, private val trendingSearchesCallBack: TrendingSearchesCallBack) : RecyclerView.Adapter<TrendingSearchesAdapter.TagViewHolder>() {

    class TagViewHolder(var binding: RvItemTerndingSearchesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TrendingSearchesAdapter.TagViewHolder(RvItemTerndingSearchesBinding.inflate(LayoutInflater.from(mContext), parent, false))

    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val item: TrendingSearchesDM = tags.get(position)
        holder.binding.tagText.text = item.searchText
        holder.binding.cnst.setBackgroundColor(getRandomColor(mContext))
        holder.binding.parent.setOnClickListener {
            this.trendingSearchesCallBack.onSeacrhItemClick(item.searchText)
        }
    }

    override fun getItemCount(): Int = tags.size

    fun getRandomColor(context: Context): Int {
        val colorCodes = context.resources.getStringArray(R.array.array_trending_searches_bg_colors)
        val randomColorCode = colorCodes.random()
        return Color.parseColor(randomColorCode)
    }

}
