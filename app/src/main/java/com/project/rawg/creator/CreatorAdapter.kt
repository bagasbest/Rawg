package com.project.rawg.creator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.project.core.domain.model.Creator
import com.project.rawg.R

class CreatorAdapter : ListAdapter<Creator, CreatorAdapter.CreatorViewHolder>(DIFF_CALLBACK) {

    // Click listener callback
    var onItemClick: ((Creator) -> Unit)? = null
    private var currentViewMode: String = "grid"

    companion object {
        private const val VIEW_TYPE_LIST = 1
        private const val VIEW_TYPE_GRID = 2

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Creator>() {
            override fun areItemsTheSame(oldItem: Creator, newItem: Creator): Boolean {
                return oldItem.creatorId == newItem.creatorId
            }

            override fun areContentsTheSame(oldItem: Creator, newItem: Creator): Boolean {
                return oldItem == newItem
            }
        }
    }

    // Called from the fragment when view mode toggles
    fun setViewMode(viewMode: String) {
        currentViewMode = viewMode
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentViewMode == "list") VIEW_TYPE_LIST else VIEW_TYPE_GRID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        val layoutId = if (viewType == VIEW_TYPE_LIST) {
            R.layout.item_creator_list
        } else {
            R.layout.item_creator_grid
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return CreatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        val creator = getItem(position)
        holder.bind(creator)
    }

    inner class CreatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        private val chipGroupGames: ChipGroup? = itemView.findViewById(R.id.chipGroupGames)

        init {
            itemView.setOnClickListener {
                // Use adapterPosition if not in a wrong state; alternatively, bindingAdapterPosition can be used
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }

        fun bind(creator: Creator) {
            Glide.with(itemView.context).load(creator.image).placeholder(R.drawable.ic_refresh).error(R.drawable.ic_broken_image).into(ivImage)
            tvName.text = creator.name
            tvPosition.text = creator.position
            chipGroupGames?.let { group ->
                group.removeAllViews()
                val games = creator.games.split(", ")
                games.forEach { game ->
                    val chip = Chip(itemView.context).apply {
                        text = game
                        isClickable = false
                        isCheckable = false
                        setChipBackgroundColorResource(R.color.black4)
                        setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    }
                    group.addView(chip)
                }
            }
        }
    }
}