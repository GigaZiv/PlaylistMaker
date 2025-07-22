package rs.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import rs.example.playlistmaker.databinding.ActivityMainBinding
import rs.example.playlistmaker.settings.ui.SettingsActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { false }

        enableEdgeToEdge()

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_activity))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top=systemBars.top, bottom = systemBars.bottom)
            insets
        }

        binding.apply {
            search.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }
            media.setOnClickListener {
                startActivity(Intent(this@MainActivity, MediaActivity::class.java))
            }
            tools.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
        }
    }
}