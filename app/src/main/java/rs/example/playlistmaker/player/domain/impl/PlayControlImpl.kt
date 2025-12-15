package rs.example.playlistmaker.player.domain.impl

import rs.example.playlistmaker.AppConstant.Companion.ZERO_TIME
import rs.example.playlistmaker.player.domain.PlayControl
import rs.example.playlistmaker.player.domain.PlayerClient
import rs.example.playlistmaker.player.util.PlayerState
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormatInt

class PlayControlImpl(private val mediaPlayer: PlayerClient, private var playerState: PlayerState) :
    PlayControl {

    override fun preparePlayer(url: String) {
        mediaPlayer.preparePlayer(url)
    }

    override fun playbackControl(): PlayerState {
        when (playerState) {
            PlayerState.PLAYING -> pausePlayer()
            PlayerState.PAUSED, PlayerState.PREPARED -> startPlayer()
        }
        return playerState
    }

    private fun startPlayer() {
        mediaPlayer.startPlayer()
        playerState = PlayerState.PLAYING
    }

    override fun pausePlayer() {
        mediaPlayer.pausePlayer()
        playerState = PlayerState.PAUSED
    }

    override fun getProgressTime(): String {
        return if (playerState == PlayerState.PREPARED) ZERO_TIME else getSimpleDateFormatInt(
            mediaPlayer.getCurrentPosition()
        )
    }

    override fun release() {
        mediaPlayer.release()
    }


    override fun setOnStateChangeListener(callback: (PlayerState) -> Unit) {
        mediaPlayer.setOnStateChangeListener { state ->
            this.playerState = state
            callback(state)
        }
    }
}