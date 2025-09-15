package rs.example.playlistmaker.search.ui

import rs.example.playlistmaker.search.domain.models.Track

sealed interface SearchState {

    object Loading : SearchState
    object AllEmpty : SearchState

    data class Content(
        val tracks: List<Track>
    ) : SearchState

    data class Error(
        val errorMessage: String
    ) : SearchState

    data class EmptyInput(
        val tracks: List<Track>
    ) : SearchState


    data class Empty(
        val message: String
    ) : SearchState

}