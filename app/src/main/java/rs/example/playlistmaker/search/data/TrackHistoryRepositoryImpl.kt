package rs.example.playlistmaker.search.data

import rs.example.playlistmaker.search.data.local.LocalStorage
import rs.example.playlistmaker.search.data.mapper.TrackMapper
import rs.example.playlistmaker.search.domain.api.TrackHistoryRepository
import rs.example.playlistmaker.search.domain.models.Track


class TrackHistoryRepositoryImpl(
    private val localStorage: LocalStorage,
) : TrackHistoryRepository {

    private val mapper = TrackMapper()

    override fun getTrackList(): List<Track> {
        return localStorage.getTrackList().map {
            mapper.trackMap(it)
        }
    }

    override fun setTrack(track: Track) {
        val trackDto = mapper.trackDtoMap(track)
        localStorage.setTrack(trackDto)
    }

    override fun clear() {
        localStorage.clear()
    }
}
