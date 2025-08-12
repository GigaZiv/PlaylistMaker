package rs.example.playlistmaker.player

import android.os.Bundle
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
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormat

class AudioPlayer : AppCompatActivity() {
    private val binding: ActivityAudioPlayerBinding by lazy {
        ActivityAudioPlayerBinding.inflate(layoutInflater)
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
            v.updatePadding(top=systemBars.top, bottom = systemBars.bottom)
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

            iwTrackTimeMillisValue.text = getSimpleDateFormat(it.trackTimeMillis)
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
        }
    }
}