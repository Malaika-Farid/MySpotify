package com.example.myspotify.models
import com.google.firebase.firestore.PropertyName
import com.google.firebase.Timestamp

data class Song(
    @PropertyName("Album") val album: String = "",
    @PropertyName("ArtistID") val artistId: String = "",
    @PropertyName("AudioURL") val audioUrl: String = "",
    @PropertyName("CoverImageURL") val imageUrl: String = "",
    @PropertyName("Duration") val duration: Int = 0,
    @PropertyName("Genre") val genre: String = "",
    @PropertyName("ReleaseDate") val releaseDate: Timestamp? = null,
    @PropertyName("Title") val title: String = ""
)