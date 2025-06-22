package rs.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import rs.example.playlistmaker.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {

    private val binding: ActivitySettingsBinding by lazy {
        ActivitySettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            backToMainFromSettings.setOnClickListener {
                this@SettingsActivity.finish()
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