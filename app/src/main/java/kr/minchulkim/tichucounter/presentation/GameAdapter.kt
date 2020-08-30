package kr.minchulkim.tichucounter.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.minchulkim.tichucounter.databinding.GameHistoryViewHolderBinding
import kr.minchulkim.tichucounter.entity.Game

class GameAdapter(val onItemClick: (Game) -> Unit) :
    ListAdapter<Game, GameAdapter.GameViewHolder>(diffCallback) {
    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }
        }

    }

    inner class GameViewHolder(private val binding: GameHistoryViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var game: Game? = null

        init {
            binding.root.setOnClickListener {
                game?.let {
                    onItemClick.invoke(it)
                }
            }
        }

        fun bind(item: Game?) {
            this.game = item
            binding.aPoint.text = item?.aPoint?.toString()
            binding.bPoint.text = item?.bPoint?.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun createViewHolder(parent: ViewGroup): GameViewHolder {
        val binding = GameHistoryViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameViewHolder(binding)
    }
}