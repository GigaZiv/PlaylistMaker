package rs.example.playlistmaker.settings.data.impl

import android.content.Context
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

    override fun getThemeSettings(): ThemeSettings {
        return ThemeSettings(sharedPreferences.getBoolean(THEME_KEY, false))
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        sharedPreferences.edit { putBoolean(THEME_KEY, settings.darkMode) }
    }

}