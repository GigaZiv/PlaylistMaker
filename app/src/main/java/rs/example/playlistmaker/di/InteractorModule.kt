package rs.example.playlistmaker.di

import rs.example.playlistmaker.playlist_creator.domain.PlayListCreatorInteractor
import rs.example.playlistmaker.playlist_creator.domain.PlayListCreatorInteractorImpl
import org.koin.dsl.module
import rs.example.playlistmaker.library.domain.FavoriteTracksInteractor
import rs.example.playlistmaker.library.domain.PlaylistInteractor
import rs.example.playlistmaker.library.domain.db.FavoriteTracksInteractorImpl
import rs.example.playlistmaker.library.domain.impl.PlaylistInteractorImpl
import rs.example.playlistmaker.player.domain.PlayControl
import rs.example.playlistmaker.player.domain.impl.PlayControlImpl
import rs.example.playlistmaker.player.util.PlayerState
import rs.example.playlistmaker.search.domain.api.TracksInteractor
import rs.example.playlistmaker.search.domain.impl.TracksInteractorImpl
import rs.example.playlistmaker.settings.domain.SettingsInteractor
import rs.example.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import rs.example.playlistmaker.sharing.domain.SharingInteractor
import rs.example.playlistmaker.sharing.domain.impl.SharingInteractorImp

val interactorModule = module {

    factory { PlayerState.PREPARED }

    single<TracksInteractor> {
        TracksInteractorImpl(get(), get())
    }

    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    single<SharingInteractor> {
        SharingInteractorImp(get())
    }

    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get())
    }

    factory <PlayControl> {
        PlayControlImpl(get(), get())
    }

    factory <FavoriteTracksInteractor> {
        FavoriteTracksInteractorImpl(get())
    }

    single<PlayListCreatorInteractor> {
        PlayListCreatorInteractorImpl(get(),get())
    }
}