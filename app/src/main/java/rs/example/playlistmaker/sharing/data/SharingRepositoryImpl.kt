package rs.example.playlistmaker.sharing.data

import android.content.Context
import rs.example.playlistmaker.sharing.domain.SharingRepository
import rs.example.playlistmaker.sharing.domain.model.EmailData
import rs.example.playlistmaker.R


class SharingRepositoryImpl(
    private val externalNavigator: ExternalNavigator,
    val context: Context
) :SharingRepository {

    override fun shareApp() {
        externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms() {
        externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport() {
        externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return context.getString(R.string.с_urlOfPracticum)
    }

    private fun getSupportEmailData(): EmailData {
        return EmailData(
            arrayOf(context.getString(R.string.с_myEmail)),
            context.getString(R.string.с_themeSupport),
            context.getString(R.string.с_massageSupport)
        )
    }

    private fun getTermsLink(): String {
        return context.getString(R.string.с_termsOfUse)
    }
}