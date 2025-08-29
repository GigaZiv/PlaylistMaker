package rs.example.playlistmaker.domain.api

interface MediaPlayerControlInterface {
    fun prepareCallback()
    fun completeCallback()
    fun startPlayer()
    fun pausePlayer()
    fun statePlayButton(state: Boolean)
    fun progressTime(progress: String)
    fun postDelayed(runnable: Runnable, millis: Long)
    fun removeCallbacks(runnable: Runnable)
}