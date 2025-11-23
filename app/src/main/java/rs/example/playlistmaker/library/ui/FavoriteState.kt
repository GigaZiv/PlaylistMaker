package rs.example.playlistmaker.library.ui

import rs.example.playlistmaker.search.domain.models.Track

sealed interface FavoriteState {
    data class Content(
        val tracks: List<Track>
    ) : FavoriteState
    object Empty : FavoriteState
    object Loading : FavoriteState
}
