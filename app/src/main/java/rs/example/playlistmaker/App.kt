package rs.example.playlistmaker

import android.app.Application
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
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

    private val sharedPreferences by lazy {
        getSharedPreferences(AppConstant.SHARED_PREF_ID, MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()

        Creator.initApplication(this)

        if (!sharedPreferences.contains(AppConstant.THEME_KEY)) {
            switchTheme(getModeNightSystemUI())
        } else {
            switchTheme(getThemePref())
        }

    }

    fun getThemePref(): Boolean {
        return sharedPreferences.getBoolean(AppConstant.THEME_KEY, false)
    }

    fun getModeNightSystemUI(): Boolean {
        return when (resources?.configuration?.uiMode?.and(
            Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }

    fun switchTheme(darkEnabled: Boolean) {
        sharedPreferences.edit { putBoolean(AppConstant.THEME_KEY, darkEnabled) }
        AppCompatDelegate.setDefaultNightMode(
            if (darkEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}