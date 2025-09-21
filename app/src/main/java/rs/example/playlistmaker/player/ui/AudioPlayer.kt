package rs.example.playlistmaker.player.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rs.example.playlistmaker.AppConstant.Companion.TRACK_ID
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.ActivityAudioPlayerBinding
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.player.ui.viewmodel.PlayerViewModel
import rs.example.playlistmaker.player.util.PlayerState
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormatLong

class AudioPlayer : AppCompatActivity() {
    private val binding: ActivityAudioPlayerBinding by lazy {
        ActivityAudioPlayerBinding.inflate(layoutInflater)
    }

    private val viewModel: PlayerViewModel by lazy {
        ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory()
        )[PlayerViewModel::class.java]
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

        viewModel.observeState().observe(this) {
            render(it)
        }

        viewModel.observeProgressTimeState().observe(this) {
            progressTimeViewUpdate(it)
        }

        init()
    }

    private fun init() = with(binding) {
        val track: Track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TRACK_ID, Track::class.java)!!
        } else {
            intent.getParcelableExtra(TRACK_ID)!!
        }

        ibPlay.setOnClickListener { viewModel.playbackControl() }

        twTitleValue.text = track.trackName
        twArtistValue.text = track.artistName

        iwTrackTimeMillisValue.text = getSimpleDateFormatLong(track.trackTimeMillis)
        iwReleaseDateValue.text = track.releaseDate?.substring(0, 4)
        iwGenresValue.text = track.primaryGenreName
        iwCollectionNameValue.text = track.collectionName
        iwCountryNameValue.text = track.country

        Glide.with(applicationContext)
            .load(
                track.artworkUrl100.replaceAfterLast(
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

        viewModel.prepare(track.previewUrl)

    }

    private fun render(state: PlayerState) {
        when (state) {
            PlayerState.PLAYING -> startPlayer()
            PlayerState.PAUSED, PlayerState.PREPARED -> pausePlayer()
        }
    }

    private fun startPlayer() = with(binding) {
        ibPlay.setImageResource(R.drawable.ic_pause_button)
    }

    private fun pausePlayer() = with(binding) {
        ibPlay.setImageResource(R.drawable.ic_play_button)
    }

    fun progressTimeViewUpdate(progressTime: String) = with(binding) {
        twProgressTime.text = progressTime
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    companion object {

        fun startActivity(context: Context, track: Track) {
            val intent = Intent(context, AudioPlayer::class.java)
            intent.putExtra(TRACK_ID, track)
            context.startActivity(intent)
        }
    }
}
