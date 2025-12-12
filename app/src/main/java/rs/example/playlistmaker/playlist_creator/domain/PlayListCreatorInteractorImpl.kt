package rs.example.playlistmaker.playlist_creator.domain

import android.net.Uri
import rs.example.playlistmaker.library.domain.PlaylistRepository
import rs.example.playlistmaker.library.domain.model.PlayList
import java.io.File

class PlayListCreatorInteractorImpl(private val repository: PlaylistRepository, private val fileRepository: FileRepository) :
    PlayListCreatorInteractor {
    override suspend fun savePlaylist(
        playListName: String,
        description: String,
        fileDir: String
    ): Long {
        val playlist = PlayList(0, playListName, description, fileDir, 0, null)
        return repository.addPlaylist(playlist)
    }

    override fun saveImage(filePath: File, savePlaylist: String, uri: Uri) {
        fileRepository.saveImage(filePath,savePlaylist, uri)
    }

}