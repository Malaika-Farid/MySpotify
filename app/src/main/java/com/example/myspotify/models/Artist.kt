package com.example.myspotify.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class Artist(
    @PropertyName("Name") val name : String = "",
    @PropertyName("Bio") val bio: String = "",
    @PropertyName("Country") val country: String = "",
    @PropertyName("FollowerCount") val followerCount: Long = 0,
    @PropertyName("Popularity") val popularity: Int = 0,
    @PropertyName("TotalTracks") val totalTracks: Int = 0,
    @PropertyName("Albums") val albums: List<String> = emptyList(),
    @PropertyName("Genre") val genre: List<String> = emptyList(),
    @PropertyName("ImageURL") val imageURL: String = "" // For image URLs
)