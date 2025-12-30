package rs.example.playlistmaker.sharing.domain

import rs.example.playlistmaker.library.domain.model.PlayList

interface SharingInteractor {
    fun shareApp()
    fun openTerms()
    fun openSupport()
    fun sharePlayList(playlist: PlayList)
}