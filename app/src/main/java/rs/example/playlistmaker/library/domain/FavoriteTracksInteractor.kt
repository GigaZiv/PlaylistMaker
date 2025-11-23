package rs.example.playlistmaker.library.domain

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.search.domain.models.Track

interface FavoriteTracksInteractor {
    fun getTracks(): Flow<List<Track>>
    suspend fun updateFavorite(track: Track): Boolean
    suspend fun getChecked(tracksId: Long): Boolean
}
