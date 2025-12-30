package rs.example.playlistmaker.search.data.mapper

import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.search.data.dto.TrackDto

class TrackMapper() {
    fun trackMap(track: TrackDto): Track {
        return Track(
            track.trackId ,
            track.trackName?: "",
            track.artistName?: "",
            track.trackTimeMillis,
            track.artworkUrl100?: "",
            track.collectionName?: "",
            track.releaseDate?: "",
            track.primaryGenreName?: "",
            track.country?: "",
            track.previewUrl?: ""
        )
    }

    fun trackDtoMap(track: Track): TrackDto {
        return TrackDto(
            track.trackId,
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.artworkUrl100,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
        )
    }
}