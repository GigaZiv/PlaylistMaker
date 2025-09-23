package rs.example.playlistmaker.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rs.example.playlistmaker.player.domain.PlayerClient
import rs.example.playlistmaker.player.domain.impl.PlayerClientImpl
import rs.example.playlistmaker.search.data.TrackHistoryRepositoryImpl
import rs.example.playlistmaker.search.data.mapper.TrackMapper
import rs.example.playlistmaker.search.data.network.TracksRepositoryImpl
import rs.example.playlistmaker.search.domain.api.TrackHistoryRepository
import rs.example.playlistmaker.search.domain.api.TracksRepository
import rs.example.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import rs.example.playlistmaker.settings.domain.SettingsRepository
import rs.example.playlistmaker.sharing.data.SharingRepositoryImpl
import rs.example.playlistmaker.sharing.domain.SharingRepository

val repositoryModule = module {

    single { TrackMapper() }

    single<TracksRepository> {
        TracksRepositoryImpl(get(), get(), androidContext())
    }

    single<TrackHistoryRepository> {
        TrackHistoryRepositoryImpl(get(),get())
    }

    single <SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single<SharingRepository> {
        SharingRepositoryImpl(get(), androidContext())
    }

    factory<PlayerClient> {
        PlayerClientImpl(get())
    }
}
