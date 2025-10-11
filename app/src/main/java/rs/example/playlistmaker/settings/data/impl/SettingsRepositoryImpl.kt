package rs.example.playlistmaker.settings.data.impl

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate
import rs.example.playlistmaker.settings.domain.SettingsRepository
import rs.example.playlistmaker.settings.domain.model.ThemeSettings
import androidx.core.content.edit
import rs.example.playlistmaker.AppConstant.Companion.SHARED_PREF_ID
import rs.example.playlistmaker.AppConstant.Companion.THEME_KEY

class SettingsRepositoryImpl(context: Context) : SettingsRepository {

     private val sharedPreferences = context.getSharedPreferences(
        SHARED_PREF_ID,
        Context.MODE_PRIVATE
    )

    private val res: Resources? = context.resources

    override fun getThemeSettings(): ThemeSettings {
        return ThemeSettings(sharedPreferences.getBoolean(THEME_KEY,
            getThemeDarkSystem()))
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        sharedPreferences.edit { putBoolean(THEME_KEY, settings.darkMode) }
    }

    override fun switchTheme(darkThemeEnabled: ThemeSettings) {
        val darkTheme = darkThemeEnabled.darkMode
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        sharedPreferences.edit {
            putBoolean(
                THEME_KEY,
                darkTheme
            )
        }
    }

    override fun getThemeDarkSystem(): Boolean {
        return when (res?.configuration?.uiMode?.and(
            Configuration.UI_MODE_NIGHT_MASK
        )) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }
    }

}