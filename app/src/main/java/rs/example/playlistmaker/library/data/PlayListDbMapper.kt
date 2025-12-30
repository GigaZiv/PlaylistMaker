package rs.example.playlistmaker.library.data

import rs.example.playlistmaker.library.data.db.entity.PlayListEntity
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.search.domain.models.Track

class PlayListDbMapper() {

    fun map(playList: PlayList, tracks: String): PlayListEntity {
        return PlayListEntity(
            playList.id,
            playList.name,
            playList.description,
            playList.imageUrl,
            playList.trackCount,
            tracks
        )
    }

    fun map(playList: PlayListEntity, tracks: MutableList<Track>): PlayList {
        return PlayList(
            playList.id,
            playList.name,
            playList.description,
            playList.imageUrl,
            playList.trackCount,
            tracks
        )
    }
}