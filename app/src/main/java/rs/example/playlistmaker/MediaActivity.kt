package rs.example.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rs.example.playlistmaker.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {

    private val binding: ActivityMediaBinding by lazy {
        ActivityMediaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}