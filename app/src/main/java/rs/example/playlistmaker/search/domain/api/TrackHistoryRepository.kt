package rs.example.playlistmaker.search.domain.api

import rs.example.playlistmaker.search.domain.models.Track

interface TrackHistoryRepository {
    fun getTrackList(): List<Track>
    fun setTrack(track: Track)
    fun clear()
}