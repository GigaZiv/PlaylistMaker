package rs.example.playlistmaker.library.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.example.playlistmaker.library.domain.PlaylistInteractor
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.library.ui.PlaylistsState

class PlaylistViewModel(val interactor: PlaylistInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<PlaylistsState>()
    fun observeState(): LiveData<PlaylistsState> = stateLiveData

    fun fill() {
        viewModelScope.launch {
            interactor.getPlayLists()
                .collect { playLists ->
                    renderState(processResult(playLists))
                }
        }
    }

    private fun processResult(albums: List<PlayList>): PlaylistsState {
        return if (albums.isEmpty()) {
            PlaylistsState.Empty
        } else {
            PlaylistsState.Content(albums)
        }
    }

    private fun renderState(state: PlaylistsState) {
        stateLiveData.postValue(state)
    }
}