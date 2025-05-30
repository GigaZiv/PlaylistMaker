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

        val t = findViewById<TextView>(R.id.back_to_main_from_settings)

        t.setOnClickListener {
            this.finish()
        }

    }
    fun onClickAction(view: View) {
        when (view.id) {
            R.id.share_app -> {
                val i = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Курс Android-разработчик")
                    putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(i)
            }
            R.id.support_app -> {
                val i = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.myEmail)))
                    putExtra(Intent.EXTRA_SUBJECT,  R.string.themeSupport)
                    putExtra(Intent.EXTRA_TEXT, R.string.massageSupport)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(i)
            }
            R.id.terms_app -> {
                val i = Intent(Intent.ACTION_VIEW).apply {
                    data = getString(R.string.termsOfUse).toUri()
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(i)
            }
        }
    }
}