package rs.example.playlistmaker.domain.api

import rs.example.playlistmaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}