package rs.example.playlistmaker.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import rs.example.playlistmaker.sharing.data.ExternalNavigator
import rs.example.playlistmaker.sharing.domain.model.EmailData
import rs.example.playlistmaker.R

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun shareLink(shareAppLink: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Intent.EXTRA_TEXT, shareAppLink)
            type = "text/plain"
        }
        prepareStartActivity(intent)
    }
    override fun openLink(termsLink: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            data = termsLink.toUri()
        }
        prepareStartActivity(intent)
    }
    override fun openEmail(supportEmailData: EmailData) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, supportEmailData.emailAddressee)
            putExtra(Intent.EXTRA_SUBJECT, supportEmailData.emailTopic)
            putExtra(Intent.EXTRA_TEXT, supportEmailData.emailMessage)
        }
        prepareStartActivity(intent)
    }

    private fun prepareStartActivity(intent: Intent) {
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context.applicationContext,
                (context.getString(R.string.c_error_message) + ": " + e.message),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
