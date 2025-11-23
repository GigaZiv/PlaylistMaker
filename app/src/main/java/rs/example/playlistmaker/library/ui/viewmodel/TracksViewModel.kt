package rs.example.playlistmaker.library.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rs.example.playlistmaker.library.domain.FavoriteTracksInteractor
import rs.example.playlistmaker.library.ui.FavoriteState
import rs.example.playlistmaker.search.domain.models.Track

class TracksViewModel(private val interactor: FavoriteTracksInteractor) : ViewModel() {

    init {
        Log.i("rs_play", "init ViewModel")
    }

    fun fillData() {
        renderState(FavoriteState.Loading)
        viewModelScope.launch {
            interactor.getTracks()
                .collect { tracks ->
                    renderState(processResult(tracks))
                }
        }
    }
    private val stateFavoriteLiveData = MutableLiveData<FavoriteState>()
    fun observeState(): LiveData<FavoriteState> = stateFavoriteLiveData
    private fun processResult(tracks: List<Track>):FavoriteState {
        return if (tracks.isEmpty()) {
            FavoriteState.Empty
        } else {
            FavoriteState.Content(tracks)
        }
    }
    private fun renderState(state: FavoriteState) {
        stateFavoriteLiveData.postValue(state)
    }
}