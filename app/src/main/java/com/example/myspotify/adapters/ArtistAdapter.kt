package com.example.myspotify.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myspotify.R
import com.example.myspotify.models.Artist

class ArtistAdapter(private var artists: List<Artist>) :
    RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.artistName)
        val country: TextView = itemView.findViewById(R.id.artistCountry)
        val image: ImageView = itemView.findViewById(R.id.artistImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.name.text = artist.name
        holder.country.text = artist.country

        // Load image from URL using Glide or Picasso
        if (artist.imageURL.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(artist.imageURL)
                .placeholder(R.drawable.ic_artist)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.placeholder_image)
        }
    }

    override fun getItemCount(): Int = artists.size

    fun updateArtists(newArtists: List<Artist>) {
        artists = newArtists
        notifyDataSetChanged()
    }
}

