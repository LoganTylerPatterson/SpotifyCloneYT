package com.weaponx.spotifyclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.weaponx.spotifyclone.R
import com.weaponx.spotifyclone.data.entities.Song

class SwipeSongAdapter : RecyclerView.Adapter<SwipeSongAdapter.SongViewHolder>(){

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvPrimary: TextView
        init {
            tvPrimary = itemView.findViewById(R.id.tvPrimary)
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.mediaId == newItem.mediaId
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    var songs: List<Song>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var onItemClickListener: ((Song) -> Unit)? = null

    fun setItemClickListener(listener: (Song) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.apply {
            val text = "${song.title} - ${song.subtitle}"
            tvPrimary.text = text
            itemView.setOnClickListener { onItemClickListener?.let { click -> click(song) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.swipe_item, parent, false))
    }
}