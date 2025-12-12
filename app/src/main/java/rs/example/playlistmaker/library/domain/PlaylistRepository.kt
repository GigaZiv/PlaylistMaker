package rs.example.playlistmaker.library.domain

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.search.domain.models.Track

interface PlaylistRepository {
    fun getPlayLists(): Flow<List<PlayList>>
    suspend fun addPlaylist(playList: PlayList): Long
    suspend fun addTrack(track: Track, playList: PlayList)
    suspend fun getTrackList(playList: PlayList): List<Track>
}
