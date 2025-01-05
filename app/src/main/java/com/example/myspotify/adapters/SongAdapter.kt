package com.example.myspotify.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspotify.MusicPlayerActivity
import com.example.myspotify.R
import com.example.myspotify.models.Song

class SongAdapter(
    private val context: Context,
    private val songList: List<Song>,
    private val onSongClickListener: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title_song)
        val imageView: ImageView = itemView.findViewById(R.id.image_song)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songList[position]
        holder.titleTextView.text = song.title
        Glide.with(context)
            .load(song.imageUrl)
            .placeholder(R.drawable.placeholder_image) // Placeholder while loading
            .error(R.drawable.error_image) // Error image if loading fails
            .into(holder.imageView)

        // Click listener to pass song details to MusicPlayerActivity
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MusicPlayerActivity::class.java)
            intent.putExtra("songTitle", song.title)
            intent.putExtra("artistName", song.artistId)
            intent.putExtra("songUrl", song.audioUrl) // Assuming songUrl is the URL of the song file
            intent.putExtra("audioUrl",song.audioUrl)
            intent.putExtra("totalTime",song.duration)
            intent.putExtra("albumName",song.album)
            intent.putExtra("imageURL",song.imageUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }
}

