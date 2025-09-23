package rs.example.playlistmaker.di

import android.content.Context
import android.media.MediaPlayer
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.example.playlistmaker.AppConstant.Companion.SHARED_PREF_ID
import rs.example.playlistmaker.search.data.local.LocalStorage
import rs.example.playlistmaker.search.data.local.SharedPreferenceLocalStorage
import rs.example.playlistmaker.search.data.network.ApiSearch
import rs.example.playlistmaker.search.data.network.NetworkClient
import rs.example.playlistmaker.search.data.network.RetrofitNetworkClient
import rs.example.playlistmaker.sharing.data.ExternalNavigator
import rs.example.playlistmaker.sharing.data.impl.ExternalNavigatorImpl

val dataModule = module {

    single<ApiSearch> {
        Retrofit.Builder().baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiSearch::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences(SHARED_PREF_ID, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    factory { MediaPlayer() }

    single<LocalStorage> {
        SharedPreferenceLocalStorage(get(), get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }

    factory<ExternalNavigator> { ExternalNavigatorImpl(get<Context>()) }
}
