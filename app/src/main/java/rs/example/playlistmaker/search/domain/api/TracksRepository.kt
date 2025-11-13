package rs.example.playlistmaker.search.domain.api

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.util.Resource

interface TracksRepository {
    fun searchTracks(expression: String): Flow<Resource<List<Track>>>
    fun getMessage(): String
}