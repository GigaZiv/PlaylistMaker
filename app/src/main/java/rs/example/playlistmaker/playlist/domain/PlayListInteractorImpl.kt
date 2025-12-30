package rs.example.playlistmaker.playlist.domain


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rs.example.playlistmaker.library.domain.PlaylistRepository
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.search.domain.models.Track
import java.time.Duration

class PlayListInteractorImpl(private val repository: PlaylistRepository) : PlayListInteractor {
    override fun getPlayList(playListId: Long): Flow<PlayList> {
        return repository.getPlayList(playListId)
    }

    override fun getTracks(playList: PlayList): Flow<List<Track>> = flow {
        emit(repository.getTrackList(playList.id))
    }

    override fun getPlayListTime(tracks: List<Track>): Int {
        var playListTime = 0L
        for (track in tracks) {
            playListTime += track.trackTimeMillis
        }
        val duration = Duration.ofMillis(playListTime)
        val minutes = duration.toMinutes()
        return (minutes).toInt()
    }

    override suspend fun delete(playlist: PlayList) {
        repository.delete(playlist)
    }

    override suspend fun removeTrack(track: Track, playList: PlayList)  {
        repository.removeTrack(track,playList)
    }


}