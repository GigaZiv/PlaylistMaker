package rs.example.playlistmaker.di

import rs.example.playlistmaker.playlist_creator.data.local.FileRepositoryImpl
import rs.example.playlistmaker.playlist_creator.domain.FileRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rs.example.playlistmaker.library.data.FavoriteTracksRepositoryImpl
import rs.example.playlistmaker.library.data.JsonMapper
import rs.example.playlistmaker.library.data.PlayListDbMapper
import rs.example.playlistmaker.library.data.PlaylistRepositoryImpl
import rs.example.playlistmaker.library.data.TrackDbMapper
import rs.example.playlistmaker.library.domain.FavoriteTracksRepository
import rs.example.playlistmaker.library.domain.PlaylistRepository
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
    single { TrackDbMapper() }
    single { PlayListDbMapper() }
    single { JsonMapper(get()) }



    single<TracksRepository> {
        TracksRepositoryImpl(get(),get(),androidContext())
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

    single<FavoriteTracksRepository> {
        FavoriteTracksRepositoryImpl(get(), get())
    }

    single<PlaylistRepository> { PlaylistRepositoryImpl(get(),get(),get()) }

    single<FileRepository> { FileRepositoryImpl(get()) }
}

