package rs.example.playlistmaker.settings.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import rs.example.playlistmaker.sharing.domain.SharingInteractor
import rs.example.playlistmaker.App
import rs.example.playlistmaker.settings.domain.SettingsInteractor
import rs.example.playlistmaker.settings.domain.model.ThemeSettings
import rs.example.playlistmaker.settings.util.ActionType
import rs.example.playlistmaker.util.Creator


class SettingsViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {
    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val sharingInteractor =
                    Creator.provideSharingInteractor(context)
                val settingsInteractor =
                    (this[APPLICATION_KEY] as App).provideSettingsInteractor()
                SettingsViewModel(
                    sharingInteractor,
                    settingsInteractor,
                )
            }
        }
    }

    fun execute(actionType: ActionType) {
        when (actionType) {
            is ActionType.Share -> sharingInteractor.shareApp()
            is ActionType.Support -> sharingInteractor.openSupport()
            is ActionType.Term -> sharingInteractor.openTerms()
            is ActionType.Theme -> updateThemeSetting(actionType.settings)
        }
    }

    fun updateThemeSetting(settings: Boolean) {
        settingsInteractor.updateThemeSetting(ThemeSettings(settings))
    }


    fun getTheme(): Boolean {
        val theme = settingsInteractor.getThemeSettings()
        return theme.darkMode
    }

}