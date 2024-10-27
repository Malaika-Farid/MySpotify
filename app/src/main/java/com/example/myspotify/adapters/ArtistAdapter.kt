package com.example.myspotify.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myspotify.R
import com.example.myspotify.models.Artist

class ArtistAdapter(private val artistList: List<Artist>) :
    RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artistImage: ImageView = itemView.findViewById(R.id.artistImage)
        val artistName: TextView = itemView.findViewById(R.id.artistName)
        val artistType: TextView = itemView.findViewById(R.id.artistType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artistList[position]
        holder.artistName.text = artist.name
        holder.artistType.text = artist.type
        holder.artistImage.setImageResource(artist.imageRes)
    }

    override fun getItemCount() = artistList.size
}
