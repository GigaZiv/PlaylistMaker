package rs.example.playlistmaker.search.domain.api

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.search.domain.models.Track

interface TracksInteractor {
    fun searchTracks(expression: String): Flow<Pair<List<Track>?, String?>>
    fun getEmptyMessage(): String
    fun getTrackList(): List<Track>
    fun setTrack(track: Track)
    fun clear()
}