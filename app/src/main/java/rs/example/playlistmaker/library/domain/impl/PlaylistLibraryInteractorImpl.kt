package rs.example.playlistmaker.library.domain.impl

import kotlinx.coroutines.flow.Flow
import rs.example.playlistmaker.library.domain.PlaylistLibraryInteractor
import rs.example.playlistmaker.library.domain.PlaylistRepository
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.search.domain.models.Track

class PlaylistLibraryInteractorImpl(private val playlistRepository: PlaylistRepository) :
    PlaylistLibraryInteractor {
    override fun getPlayLists(): Flow<List<PlayList>> {
        return playlistRepository.getPlayLists()
    }

    override suspend fun addTrack(track: Track, playList: PlayList): Boolean {
        val tracks = playlistRepository.getTrackList(playList.id)
        return if (tracks.isEmpty() || !tracks.contains(track)) {
            playlistRepository.addTrack(track, playList)
            true
        } else false
    }
}

