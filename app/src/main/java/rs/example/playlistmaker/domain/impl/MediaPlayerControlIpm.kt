package rs.example.playlistmaker.domain.impl

import android.media.MediaPlayer
import rs.example.playlistmaker.AppConstant.Companion.DELAY
import rs.example.playlistmaker.domain.api.MediaPlayerControlInterface
import rs.example.playlistmaker.domain.models.Track
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormatInt


class MediaPlayerControlIpm(val mediaInterface: MediaPlayerControlInterface) {
    private var playerState = STATE_DEFAULT
    private val mediaPlayer by lazy {
        MediaPlayer()
    }

    fun preparePlayer(track: Track) {

        mediaPlayer.apply {
            if (track.previewUrl.isNotEmpty()) {
                mediaInterface.statePlayButton(false)
                setDataSource(track.previewUrl)
                prepareAsync()
            }
            setOnPreparedListener {
                mediaInterface.apply {
                    prepareCallback()
                    statePlayButton(true)
                }
                playerState = STATE_PREPARED
            }
            setOnCompletionListener {
                mediaInterface.completeCallback()
                playerState = STATE_PREPARED
            }
        }
    }

    fun playControl() {
        when (playerState) {
            STATE_PLAYING -> pausePlayer()
            STATE_PREPARED, STATE_PAUSED -> startPlayer()
        }
    }

    fun startPlayer() {
        mediaInterface.startPlayer()
        mediaPlayer.start()
        playerState = STATE_PLAYING
    }

    fun pausePlayer() {
        mediaInterface.pausePlayer()
        mediaPlayer.pause()
        playerState = STATE_PAUSED
    }

    fun progressTrack(): Runnable {
        return object : Runnable {
            override fun run() {
                when (playerState) {
                    STATE_PLAYING -> {
                        mediaInterface.progressTime(getSimpleDateFormatInt(mediaPlayer.currentPosition))
                        mediaInterface.postDelayed(this, DELAY)
                    }

                    STATE_PAUSED -> mediaInterface.removeCallbacks(this)
                    STATE_PREPARED -> {
                        mediaInterface.prepareCallback()
                        mediaInterface.removeCallbacks(this)
                    }
                }
            }
        }
    }

    fun releasePlayer() {
        mediaPlayer.release()
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}