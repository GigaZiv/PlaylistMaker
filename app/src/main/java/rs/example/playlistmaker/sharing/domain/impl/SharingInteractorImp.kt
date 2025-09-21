package rs.example.playlistmaker.sharing.domain.impl

import rs.example.playlistmaker.sharing.domain.SharingInteractor
import rs.example.playlistmaker.sharing.domain.SharingRepository

class SharingInteractorImp(private val sharingRepository: SharingRepository) : SharingInteractor {
    override fun shareApp() {
        sharingRepository.shareApp()
    }

    override fun openTerms() {
        sharingRepository.openTerms()
    }

    override fun openSupport() {
        sharingRepository.openSupport()
    }

}
