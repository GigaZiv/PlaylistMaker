package rs.example.playlistmaker.library.domain

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.search.domain.models.Track

interface PlaylistInteractor {
    fun getPlayLists(): Flow<List<PlayList>>
    suspend fun addTrack(track: Track, playList: PlayList): Boolean
}
