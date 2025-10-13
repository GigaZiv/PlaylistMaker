package rs.example.playlistmaker.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.example.playlistmaker.library.ui.viewmodel.PlaylistViewModel
import rs.example.playlistmaker.library.ui.viewmodel.TracksViewModel
import rs.example.playlistmaker.player.ui.viewmodel.PlayerViewModel
import rs.example.playlistmaker.search.ui.view_model.SearchViewModel
import rs.example.playlistmaker.settings.view_model.SettingsViewModel

val viewModelModule = module {

    viewModel {
        PlayerViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        SettingsViewModel(get(), get())
    }

    viewModel {
        PlaylistViewModel()
    }
    viewModel {
        TracksViewModel()
    }

}
