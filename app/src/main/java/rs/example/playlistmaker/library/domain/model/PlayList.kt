package rs.example.playlistmaker.library.domain.model

import rs.example.playlistmaker.search.domain.models.Track

data class PlayList(
    var id: Long,
    var name: String,
    var description: String,
    val imageUrl: String,
    var trackCount: Long,
    var tracks: MutableList<Track>
)