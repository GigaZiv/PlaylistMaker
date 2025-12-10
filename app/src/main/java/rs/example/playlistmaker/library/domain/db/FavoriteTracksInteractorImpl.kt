package rs.example.playlistmaker.library.domain.db

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.library.domain.FavoriteTracksInteractor
import rs.example.playlistmaker.library.domain.FavoriteTracksRepository
import rs.example.playlistmaker.search.domain.models.Track

class FavoriteTracksInteractorImpl(
    private val favoriteTracksRepository: FavoriteTracksRepository): FavoriteTracksInteractor {

    override fun getTracks(): Flow<List<Track>> {
        return favoriteTracksRepository.getFavoriteTracks()
    }

    override suspend fun updateFavorite(track: Track): Boolean {
        var isChecked = false
        favoriteTracksRepository.getFavoriteChecked().collect {tracksId ->
            isChecked = if (tracksId.contains(track.trackId)) {
                favoriteTracksRepository.deleteFavoriteTrack(track)
                false
            } else {
                favoriteTracksRepository.addFavoriteTrack(track)
                true
            }
        }
        return isChecked
    }

    override suspend fun getChecked(tracksId: Long) : Boolean {
        var isChecked = false
        favoriteTracksRepository.getFavoriteChecked().collect { id ->
            isChecked = id.contains(tracksId)
        }
        return isChecked
    }

}
