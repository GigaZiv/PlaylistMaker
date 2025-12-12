package rs.example.playlistmaker.player.domain

import rs.example.playlistmaker.player.util.PlayerState

interface PlayControl {
    fun preparePlayer(url: String)
    fun release()
    fun playbackControl(): PlayerState
    fun getProgressTime(): String
    fun setOnStateChangeListener(callback: (PlayerState) -> Unit)
    fun pausePlayer()
}
