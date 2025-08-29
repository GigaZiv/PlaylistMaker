package rs.example.playlistmaker.domain.impl

import rs.example.playlistmaker.domain.api.TracksInteractor
import rs.example.playlistmaker.domain.api.TracksRepository
import java.util.concurrent.Executors

class TracksInteractorImp(private val repository: TracksRepository) : TracksInteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(
        expression: String,
        consumer: TracksInteractor.TracksConsumer,
        errorHandler: TracksInteractor.ErrorHandler
    ) {
        executor.execute {
            val result = try {
                repository.searchTracks(expression)
            } catch (e: Exception) {
                errorHandler.handle(e)
                return@execute
            }
            consumer.consume(result)
        }
    }
}
