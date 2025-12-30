package rs.example.playlistmaker.playlist.domain

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.search.domain.models.Track

interface PlayListInteractor {
    fun getPlayList(playListId: Long): Flow<PlayList>
    fun getTracks(playList: PlayList):  Flow<List<Track>>
    fun getPlayListTime(tracks: List<Track>): Int
    suspend fun delete(playlist: PlayList)
    suspend fun removeTrack(track: Track, playList: PlayList)
}
