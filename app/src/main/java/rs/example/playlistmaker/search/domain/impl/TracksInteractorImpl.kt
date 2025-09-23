package rs.example.playlistmaker.search.domain.impl

import rs.example.playlistmaker.search.domain.api.TrackHistoryRepository
import rs.example.playlistmaker.search.domain.api.TracksInteractor
import rs.example.playlistmaker.search.domain.api.TracksRepository
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.util.Resource
import java.util.concurrent.Executors

class TracksInteractorImpl(
    private val searchRepository: TracksRepository,
    private val historyRepository: TrackHistoryRepository
) : TracksInteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(
        expression: String,
        consumer: TracksInteractor.TracksConsumer,
    ) {
        executor.execute {
            when (val resource = searchRepository.searchTracks(expression)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }

                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }
    override fun getEmptyMessage(): String {
        return searchRepository.getMessage()
    }
    override fun getTrackList(): List<Track> {
        return historyRepository.getTrackList()
    }
    override fun setTrack(track: Track) {
        historyRepository.setTrack(track)
    }
    override fun clear() {
        historyRepository.clear()
    }
}
