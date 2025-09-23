package rs.example.playlistmaker.search.data.local

import rs.example.playlistmaker.search.data.dto.TrackDto

interface LocalStorage {
    fun getTrackList(): List<TrackDto>
    fun clear()
    fun setTrack(track: TrackDto)
}