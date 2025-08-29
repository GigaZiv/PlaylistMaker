package rs.example.playlistmaker.presentation.ui

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
import rs.example.playlistmaker.creator.Creator
import rs.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import rs.example.playlistmaker.domain.api.MediaPlayerControlInterface
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormatLong


class AudioPlayer : AppCompatActivity(), MediaPlayerControlInterface {
    private val getPlayerControl by lazy {
        Creator.getMediaPlayerControl(this)
    }
    private val getTrackStorePreference by lazy {
        Creator.getTrackStorePreferences(
            getSharedPreferences(
                AppConstant.SHARED_PREF_ID, MODE_PRIVATE
            )
        )
    }
    private val binding: ActivityAudioPlayerBinding by lazy {
        ActivityAudioPlayerBinding.inflate(layoutInflater)
    }
    private val threadHandler by lazy {
        Handler(Looper.getMainLooper())
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
        getTrackStorePreference.getTrack(
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

            getPlayerControl.preparePlayer(it)
            ibPlay.setOnClickListener { getPlayerControl.playControl() }
        }
    }

    override fun onPause() {
        super.onPause()
        getPlayerControl.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPlayerControl.releasePlayer()
    }

    override fun prepareCallback() = with(binding) {
        ibPlay.setImageResource(R.drawable.ic_play_button)
        twProgressTime.text = getString(R.string.c_track_time)
    }

    override fun completeCallback() = with(binding) {
        twProgressTime.text = getString(R.string.c_track_time)
    }

    override fun startPlayer() {
        binding.ibPlay.setImageResource(R.drawable.ic_pause_button)
        threadHandler.post(getPlayerControl.progressTrack())
    }

    override fun pausePlayer() = with(binding) {
        ibPlay.setImageResource(R.drawable.ic_play_button)
    }

    override fun statePlayButton(state: Boolean) = with(binding) {
        ibPlay.isEnabled = state
    }

    override fun progressTime(progress: String) = with(binding) {
        twProgressTime.text = progress
    }

    override fun postDelayed(runnable: Runnable, millis: Long) {
        threadHandler.postDelayed(runnable, millis)
    }

    override fun removeCallbacks(runnable: Runnable) {
        threadHandler.removeCallbacks(runnable)
    }

}