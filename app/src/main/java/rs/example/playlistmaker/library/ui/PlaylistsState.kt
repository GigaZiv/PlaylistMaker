package rs.example.playlistmaker.library.ui

import rs.example.playlistmaker.library.domain.model.PlayList

sealed interface PlaylistsState {
    data class Content(
        val items: List<PlayList>
    ) : PlaylistsState

    object Empty : PlaylistsState
}