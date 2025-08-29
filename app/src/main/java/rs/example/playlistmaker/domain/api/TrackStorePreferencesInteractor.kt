package rs.example.playlistmaker.domain.api

import rs.example.playlistmaker.domain.models.Track

interface TrackStorePreferencesInteractor {
    fun setTrack(track: Track)

    fun readTracks(): MutableList<Track>

    fun getTrack(trackId: String): Track?

    fun clearTracks()

}