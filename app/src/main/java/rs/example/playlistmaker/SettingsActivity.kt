package rs.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<TextView>(R.id.back_to_main_from_settings).apply {
            setOnClickListener { this@SettingsActivity.finish() }
        }

    }

    fun onClickAction(view: View) {
        when (view.id) {
            R.id.share_app -> {
                startActivity(Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Курс Android-разработчик")
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.urlOfPracticum))
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }

            R.id.support_app -> {
                startActivity(Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.myEmail)))
                    putExtra(Intent.EXTRA_SUBJECT, getString(R.string.themeSupport))
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.massageSupport))
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }

            R.id.terms_app -> {
                startActivity(Intent(Intent.ACTION_VIEW).apply {
                    data = getString(R.string.termsOfUse).toUri()
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }
    }
}