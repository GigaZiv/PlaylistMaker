package rs.example.playlistmaker.player.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rs.example.playlistmaker.AppConstant.Companion.DELAY
import rs.example.playlistmaker.library.domain.FavoriteTracksInteractor
import rs.example.playlistmaker.library.domain.PlaylistInteractor
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.player.domain.PlayControl
import rs.example.playlistmaker.player.util.PlayerState
import rs.example.playlistmaker.search.domain.models.Track

class PlayerViewModel(
    private val playerInteractor: PlayControl,
    private val favoriteInteractor: FavoriteTracksInteractor,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    init {
        playerInteractor.setOnStateChangeListener { state ->
            stateLiveData.postValue(state)
            val progressTime = playerInteractor.getProgressTime()
            stateProgressTimeLiveData.postValue(progressTime)
            if (state == PlayerState.PREPARED) cancelTimer()
        }
    }

    private val stateLiveData = MutableLiveData(PlayerState.PREPARED)
    fun observeState(): LiveData<PlayerState> = stateLiveData

    private val stateProgressTimeLiveData = MutableLiveData<String>()
    fun observeProgressTimeState(): LiveData<String> = stateProgressTimeLiveData

    private val stateFavoriteData = MutableLiveData<Boolean>()
    fun observeFavoriteState(): LiveData<Boolean> = stateFavoriteData

    private val playListsLiveData = MutableLiveData<List<PlayList>>()
    fun observePlaylistState(): LiveData<List<PlayList>> = playListsLiveData

    private val addLiveData = MutableLiveData<Pair<String, Boolean>>()
    fun observeAddDate(): LiveData<Pair<String, Boolean>> = addLiveData

    private var timerJob: Job? = null
    private fun startTimer(state: PlayerState) {
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (state == PlayerState.PLAYING) {
                delay(DELAY)
                stateProgressTimeLiveData.postValue(playerInteractor.getProgressTime())
            }
        }
    }

    fun prepare(track: Track) {
        viewModelScope.launch {
            playerInteractor.preparePlayer(track.previewUrl)
            getChecked(track)
        }
    }

    fun playbackControl() {
        val state = playerInteractor.playbackControl()
        renderState(state)
        if (state == PlayerState.PLAYING) startTimer(state) else cancelTimer()

    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.release()
    }

    fun onPause() {
        playerInteractor.pausePlayer()
        renderState(PlayerState.PAUSED)
    }

    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
    }

    private fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun onFavoriteClicked(track: Track) {
        viewModelScope.launch {
            renderFavoriteState(favoriteInteractor.updateFavorite(track))
        }
    }

    private fun getChecked(track: Track) {
        viewModelScope.launch {
            renderFavoriteState(favoriteInteractor.getChecked(track.trackId))
        }
    }

    private fun renderFavoriteState(isChecked: Boolean) {
        stateFavoriteData.postValue(isChecked)
    }

    private fun renderToastState(result: Pair<String, Boolean>) {
        addLiveData.postValue(result)
    }


    fun addToPlaylist(track: Track, playList: PlayList) {
        viewModelScope.launch {
            renderToastState(Pair(playList.name, playlistInteractor.addTrack(track, playList)))
     }
    }

    fun renderPlayLists() {
        viewModelScope.launch {
            playlistInteractor.getPlayLists()
                .collect { playLists ->
                    playListsLiveData.postValue(playLists)
                }
        }
    }
}