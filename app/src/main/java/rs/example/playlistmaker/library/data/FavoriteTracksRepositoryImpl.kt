package rs.example.playlistmaker.library.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rs.example.playlistmaker.library.data.db.TrackDao
import rs.example.playlistmaker.library.data.db.entity.TracksEntity
import rs.example.playlistmaker.library.domain.FavoriteTracksRepository
import rs.example.playlistmaker.search.domain.models.Track
import java.util.Date


class FavoriteTracksRepositoryImpl(
    private val dao: TrackDao,
    private val trackDbMapper: TrackDbMapper,
) : FavoriteTracksRepository {


    override fun getFavoriteTracks(): Flow<List<Track>> = flow {
        val tracks = dao.getFavoriteTracks()
        emit(convertFromTrackEntity(tracks))
    }

    override fun getFavoriteChecked(): Flow<List<Long>> = flow {
        val tracksId = dao.getTracksId()
        emit(tracksId)
    }

    override suspend fun addFavoriteTrack(track: Track) {
        val trackEntity = convertToTrackEntity(track)
        dao.insertTrack(trackEntity)
    }

    override suspend fun deleteFavoriteTrack(track: Track) {
        val trackEntity = convertToTrackEntity(track)
        dao.delete(trackEntity)
    }

    private fun convertFromTrackEntity(tracks: List<TracksEntity>): List<Track> {
        return tracks.map { track -> trackDbMapper.map(track) }
    }

    private fun convertToTrackEntity(track: Track): TracksEntity {
        return trackDbMapper.map(track, Date())
    }
}
