package com.weaponx.spotifyclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.weaponx.spotifyclone.R
import com.weaponx.spotifyclone.data.entities.Song
import com.weaponx.spotifyclone.databinding.ListItemBinding
import javax.inject.Inject

class SongAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>(){

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivItemImage: ImageView
        val tvPrimary: TextView
        val tvSecondary: TextView
        init {
            ivItemImage = itemView.findViewById(R.id.ivItemImage)
            tvPrimary = itemView.findViewById(R.id.tvPrimary)
            tvSecondary = itemView.findViewById(R.id.tvSecondary)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.apply {
            tvPrimary.text = song.title
            tvSecondary.text = song.subtitle
            glide.load(song.imageUrl).into(ivItemImage)

            itemView.setOnClickListener { onItemClickListener?.let { click -> click(song) } }
        }
    }
}