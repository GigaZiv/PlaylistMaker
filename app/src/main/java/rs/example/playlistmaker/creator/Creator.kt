package rs.example.playlistmaker.creator

import android.content.SharedPreferences
import rs.example.playlistmaker.data.TracksRepositoryImpl
import rs.example.playlistmaker.data.network.RetrofitNetworkClient
import rs.example.playlistmaker.domain.api.MediaPlayerControlInterface
import rs.example.playlistmaker.domain.api.TrackStorePreferencesInteractor
import rs.example.playlistmaker.domain.api.TracksInteractor
import rs.example.playlistmaker.domain.api.TracksRepository
import rs.example.playlistmaker.domain.impl.MediaPlayerControlIpm
import rs.example.playlistmaker.domain.impl.TrackStorePreferencesImp
import rs.example.playlistmaker.domain.impl.TracksInteractorImp

//**
// Удалить TrackStorePreferences
//


object Creator {
    private fun getTrackRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTrackInteractor(): TracksInteractor {
        return TracksInteractorImp(getTrackRepository())
    }

    fun getTrackStorePreferences(sharedPreference: SharedPreferences): TrackStorePreferencesInteractor {
        return TrackStorePreferencesImp(sharedPreference)
    }

    fun getMediaPlayerControl(mediaPlayerControlInterface: MediaPlayerControlInterface): MediaPlayerControlIpm {
        return MediaPlayerControlIpm(mediaPlayerControlInterface)
    }
}