package rs.example.playlistmaker.settings.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import rs.example.playlistmaker.App
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings_activity))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top=systemBars.top, bottom = systemBars.bottom)
            insets
        }

        binding.apply {

            setSupportActionBar(tbSetting)

            tbSetting.setNavigationOnClickListener {
                this@SettingsActivity.finish()
            }

            swModeUi.apply {
                isChecked = (applicationContext as App).getThemePref()
                setOnCheckedChangeListener { sender, isChecked ->
                    (applicationContext as App).switchTheme(sender.isChecked)
                }
            }

            shareApp.setOnClickListener {
                startActivity(Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Курс Android-разработчик")
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.с_urlOfPracticum))
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }

            supportApp.setOnClickListener {
                startActivity(Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.с_myEmail)))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.с_themeSupport))
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.с_massageSupport))
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
            termsApp.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = getString(R.string.с_termsOfUse).toUri()
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }
    }
}