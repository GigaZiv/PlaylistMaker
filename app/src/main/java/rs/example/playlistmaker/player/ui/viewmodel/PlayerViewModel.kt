package rs.example.playlistmaker.player.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rs.example.playlistmaker.AppConstant.Companion.DELAY
import rs.example.playlistmaker.player.domain.PlayControl
import rs.example.playlistmaker.player.util.PlayerState

class PlayerViewModel(private val playerInteractor: PlayControl) :
    ViewModel() {


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

    private var timerJob: Job? = null

    private fun startTimer(state: PlayerState) {
        timerJob = viewModelScope.launch(Dispatchers.Default) {
            while (state == PlayerState.PLAYING) {
                delay(DELAY)
                stateProgressTimeLiveData.postValue(playerInteractor.getProgressTime())
            }
        }
    }

    fun prepare(url: String) {
        playerInteractor.preparePlayer(url)
    }

    fun playbackControl() {
        val state = playerInteractor.playbackControl()
        renderState(state)
        if (state == PlayerState.PLAYING) startTimer(state) else cancelTimer()

    }

    private fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
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
}
