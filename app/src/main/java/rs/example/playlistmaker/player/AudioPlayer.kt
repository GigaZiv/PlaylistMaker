package rs.example.playlistmaker.player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rs.example.playlistmaker.AppConstant
import rs.example.playlistmaker.R
import rs.example.playlistmaker.api.TrackHistory
import rs.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormatInt
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormatLong
import rs.example.playlistmaker.AppConstant.Companion.DELAY

class AudioPlayer : AppCompatActivity() {
    private var playerState = STATE_DEFAULT

    private val binding: ActivityAudioPlayerBinding by lazy {
        ActivityAudioPlayerBinding.inflate(layoutInflater)
    }
    private val threadHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    private val mediaPlayer by lazy {
        MediaPlayer()
    }
    private val trackHistory by lazy {
        TrackHistory(
            getSharedPreferences(
                AppConstant.SHARED_PREF_ID, MODE_PRIVATE
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.audio_player))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        binding.apply {
            iwBack.setOnClickListener { finish() }
        }

        init()
    }

    private fun init() = with(binding) {
        trackHistory.getTrack(
            intent.getStringExtra("trackId").toString()
        )?.let {
            twTitleValue.text = it.trackName
            twArtistValue.text = it.artistName

            iwTrackTimeMillisValue.text = getSimpleDateFormatLong(it.trackTimeMillis)
            iwReleaseDateValue.text = it.releaseDate?.substring(0, 4)
            iwGenresValue.text = it.primaryGenreName
            iwCollectionNameValue.text = it.collectionName
            iwCountryNameValue.text = it.country

            Glide.with(applicationContext)
                .load(
                    it.artworkUrl100.replaceAfterLast(
                        '/',
                        "512x512bb.jpg"
                    )
                )
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .placeholder(R.drawable.ic_track_default)
                .transform(
                    RoundedCorners(
                        applicationContext.resources.getDimensionPixelSize(
                            R.dimen.crt_2
                        )
                    )
                )
                .into(ivTrackImageLarge)

            mediaPlayer.apply {
                if (it.previewUrl.isNotEmpty()) {
                    setDataSource(it.previewUrl)
                    prepareAsync()
                }
                setOnPreparedListener {
                    playerState = STATE_PREPARED
                }
                setOnCompletionListener {
                    playerState = STATE_PREPARED
                }
            }
        }
        ibPlay.setOnClickListener {
            when (playerState) {
                STATE_PLAYING -> pausePlayer()
                STATE_PREPARED, STATE_PAUSED -> startPlayer()
            }
        }
    }

    private fun startPlayer() = with(binding) {
        mediaPlayer.start()
        ibPlay.setImageResource(R.drawable.ic_pause_button)
        playerState = STATE_PLAYING
        threadHandler.post(progressTrack())
    }


    private fun progressTrack(): Runnable = with(binding) {
        return object : Runnable {
            override fun run() {
                when (playerState) {
                    STATE_PLAYING -> {
                        twProgressTime.text = getSimpleDateFormatInt(mediaPlayer.currentPosition)
                        threadHandler.postDelayed(this, DELAY)
                    }
                    STATE_PAUSED -> threadHandler.removeCallbacks(this)
                    STATE_PREPARED -> {
                        threadHandler.removeCallbacks(this)
                        ibPlay.setImageResource(R.drawable.ic_play_button)
                        twProgressTime.text = getString(R.string.c_track_time)
                    }
                }
            }
        }
    }

    private fun pausePlayer() = with(binding) {
        mediaPlayer.pause()
        ibPlay.setImageResource(R.drawable.ic_play_button)
        playerState = STATE_PAUSED
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}