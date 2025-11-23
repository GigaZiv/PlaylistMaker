package rs.example.playlistmaker.library.domain

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.search.domain.models.Track

interface FavoriteTracksRepository {
    fun getFavoriteTracks(): Flow<List<Track>>
    fun getFavoriteChecked(): Flow<List<Long>>
    suspend fun addFavoriteTrack(track: Track)
    suspend fun deleteFavoriteTrack(track: Track)
}
