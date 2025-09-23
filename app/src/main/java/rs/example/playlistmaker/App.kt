package rs.example.playlistmaker

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rs.example.playlistmaker.di.dataModule
import rs.example.playlistmaker.di.interactorModule
import rs.example.playlistmaker.di.repositoryModule
import rs.example.playlistmaker.di.viewModelModule
import rs.example.playlistmaker.settings.domain.SettingsRepository
import kotlin.getValue

class App: Application() {

    private val repository: SettingsRepository by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }

        repository.switchTheme(repository.getThemeSettings())

    }
}