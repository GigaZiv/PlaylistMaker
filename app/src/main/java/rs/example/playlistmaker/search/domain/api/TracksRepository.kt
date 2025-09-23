package rs.example.playlistmaker.search.domain.api

import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.util.Resource

interface TracksRepository {
    fun searchTracks(expression: String): Resource<List<Track>>
    fun getMessage(): String
}