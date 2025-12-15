package rs.example.playlistmaker.di

import rs.example.playlistmaker.playlist_creator.ui.PlayListCreatorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.example.playlistmaker.library.ui.view_model.PlaylistViewModel
import rs.example.playlistmaker.library.ui.view_model.TracksViewModel
import rs.example.playlistmaker.main.ui.MainActivityViewModel
import rs.example.playlistmaker.player.ui.view_model.PlayerViewModel
import rs.example.playlistmaker.search.ui.view_model.SearchViewModel
import rs.example.playlistmaker.settings.ui.view_model.SettingsViewModel

val viewModelModule = module {

    viewModel {
        PlayerViewModel(get(),get(),get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        SettingsViewModel(get(), get())
    }
    viewModel {
        PlaylistViewModel(get())
    }
    viewModel {
        TracksViewModel(get())
    }

    viewModel { PlayListCreatorViewModel(get()) }

    viewModel { MainActivityViewModel() }
}
