package rs.example.playlistmaker.player.domain

import rs.example.playlistmaker.player.util.PlayerState

interface PlayerClient {
    fun preparePlayer(url: String?)
    fun startPlayer()
    fun pausePlayer()
    fun getCurrentPosition(): Int
    fun release()
    fun setOnStateChangeListener(callback: (PlayerState) -> Unit)
}
