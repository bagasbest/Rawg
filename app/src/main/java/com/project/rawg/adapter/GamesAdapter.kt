package com.project.rawg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.core.domain.model.Games
import com.project.rawg.databinding.ItemGamesBinding

class GamesAdapter : ListAdapter<Games, GamesAdapter.GamesViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Games) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GamesViewHolder {
        val view = ItemGamesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class GamesViewHolder(private val binding: ItemGamesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Games) {
            with(binding) {
                ivWindows.visibility = View.GONE
                ivPlaystation.visibility = View.GONE
                ivApple.visibility = View.GONE
                ivNintendo.visibility = View.GONE
                ivXbox.visibility = View.GONE

                Glide.with(itemView.context)
                    .load(data.backgroundImage)
                    .into(ivImage)
                tvName.text = data.name
                tvCategory.text = data.genres

                val platform = data.parentPlatform
                if (platform.contains("PC")) {
                    ivWindows.visibility = View.VISIBLE
                }
                if (platform.contains("PlayStation")) {
                    ivPlaystation.visibility = View.VISIBLE
                }
                if (platform.contains("Apple Macintosh")) {
                    ivApple.visibility = View.VISIBLE
                }
                if (platform.contains("Nintendo")) {
                    ivNintendo.visibility = View.VISIBLE
                }
                if (platform.contains("Xbox")) {
                    ivXbox.visibility = View.VISIBLE
                }
            }
        }


        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }


    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Games> =
            object : DiffUtil.ItemCallback<Games>() {
                override fun areItemsTheSame(
                    oldItem: Games,
                    newItem: Games
                ): Boolean {
                    return oldItem.gamesId == newItem.gamesId
                }

                override fun areContentsTheSame(
                    oldItem: Games,
                    newItem: Games
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}