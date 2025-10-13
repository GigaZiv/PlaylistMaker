package rs.example.playlistmaker.library.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.tabs.TabLayoutMediator
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.ActivityMediaBinding
import rs.example.playlistmaker.library.ui.adapter.LibraryViewPagerAdapter

class LibraryActivity : AppCompatActivity() {

    private val binding: ActivityMediaBinding by lazy {
        ActivityMediaBinding.inflate(layoutInflater)
    }

    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.media_activity))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        binding.tbMedia.setNavigationOnClickListener {
            this.finish()
        }

        binding.viewPager.adapter = LibraryViewPagerAdapter(supportFragmentManager, lifecycle)

        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.c_favorite_tracks)
                1 -> tab.text = getString(R.string.с_playlist)
            }
        }
        tabMediator.attach()
    }
    companion object {

        fun show(context: Context) {
            val intent = Intent(context, LibraryActivity::class.java)
            context.startActivity(intent)
        }
    }
}