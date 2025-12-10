package rs.example.playlistmaker.library.data

import rs.example.playlistmaker.library.data.db.entity.TracksEntity
import rs.example.playlistmaker.search.domain.models.Track
import java.util.Date

class TrackDbMapper {
    fun map(track: Track, updateTime: Date): TracksEntity {
        return TracksEntity(
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
            updateTime
        )
    }

    fun map(track: TracksEntity): Track {
        return Track(
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
