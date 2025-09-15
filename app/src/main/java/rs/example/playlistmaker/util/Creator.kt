package rs.example.playlistmaker.util

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import rs.example.playlistmaker.player.data.impl.PlayerClientImpl
import rs.example.playlistmaker.sharing.domain.SharingInteractor
import rs.example.playlistmaker.sharing.domain.SharingRepository
import rs.example.playlistmaker.sharing.domain.impl.SharingInteractorImp
import rs.example.playlistmaker.AppConstant
import rs.example.playlistmaker.search.data.network.RetrofitNetworkClient
import rs.example.playlistmaker.search.domain.api.TracksInteractor
import rs.example.playlistmaker.search.domain.api.TracksRepository
import rs.example.playlistmaker.player.domain.PlayControl
import rs.example.playlistmaker.player.domain.impl.PlayControlImpl
import rs.example.playlistmaker.search.data.TrackHistoryRepositoryImpl
import rs.example.playlistmaker.search.data.local.LocalStorage
import rs.example.playlistmaker.search.data.network.TracksRepositoryImpl
import rs.example.playlistmaker.search.domain.api.TrackHistoryRepository
import rs.example.playlistmaker.search.domain.impl.TracksInteractorImpl
import rs.example.playlistmaker.sharing.data.SharingRepositoryImpl
import rs.example.playlistmaker.sharing.data.impl.ExternalNavigatorImpl

object Creator {
    private lateinit var application: Application

    fun initApplication(app: Application) {
        this.application = app

    }

    fun createPlayControl(): PlayControl {
        return PlayControlImpl(PlayerClientImpl())
    }

    private fun getTrackRepository(context: Context): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideTrackInteractor(context: Context): TracksInteractor {
        return TracksInteractorImpl(
            getTrackRepository(
                context
            )
        )
    }

    fun getHistoryRepository(context: Context): TrackHistoryRepository {
        return TrackHistoryRepositoryImpl(
            LocalStorage(
                context.getSharedPreferences(
                    AppConstant.Companion.SHARED_PREF_ID,
                    MODE_PRIVATE
                )
            )
        )
    }

    fun provideSharingInteractor(context: Context): SharingInteractor {
        return SharingInteractorImp(getSharingRepository(context))
    }

    fun getSharingRepository(context: Context): SharingRepository {
        val externalNavigator = ExternalNavigatorImpl(context)
        return SharingRepositoryImpl(externalNavigator, context)
    }

}