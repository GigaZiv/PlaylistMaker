package rs.example.playlistmaker.library.data

import rs.example.playlistmaker.library.data.db.entity.PlayListEntity
import rs.example.playlistmaker.library.domain.model.PlayList

class PlayListDbMapper {
    fun map(playList: PlayList): PlayListEntity {
        return PlayListEntity(
            playList.id,
            playList.name,
            playList.description,
            playList.imageUrl,
            playList.trackCount,
            playList.tracks
        )
    }
    fun map(playList: PlayListEntity): PlayList {
        return PlayList(
            playList.id,
            playList.name,
            playList.description,
            playList.imageUrl,
            playList.trackCount,
            playList.tracks
        )
    }
}