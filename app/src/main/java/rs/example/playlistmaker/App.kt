package rs.example.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import rs.example.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import rs.example.playlistmaker.settings.domain.SettingsInteractor
import rs.example.playlistmaker.settings.domain.SettingsRepository
import rs.example.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import rs.example.playlistmaker.util.Creator

class App: Application() {

    fun provideSettingsInteractor(): SettingsInteractor {
        return SettingsInteractorImpl(getSettingsRepository())
    }

    private fun getSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(this)
    }

    override fun onCreate() {
        super.onCreate()

        Creator.initApplication(this)

        switchTheme(provideSettingsInteractor().getThemeSettings().darkMode)

    }

    fun switchTheme(darkEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}