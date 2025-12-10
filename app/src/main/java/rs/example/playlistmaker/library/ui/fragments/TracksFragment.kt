package rs.example.playlistmaker.library.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.databinding.TracksFragmentBinding
import rs.example.playlistmaker.library.ui.FavoriteState
import rs.example.playlistmaker.library.ui.viewmodel.TracksViewModel
import rs.example.playlistmaker.player.ui.AudioPlayer
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.search.ui.adapter.SearchAdapter

class TracksFragment : Fragment() {

    private val viewModel by viewModel<TracksViewModel>()

    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 100L
        fun newInstance() = TracksFragment().apply {
        }
    }

    private var binding: TracksFragmentBinding? = null
    private val tracks = mutableListOf<Track>()

    private var adapter = SearchAdapter(tracks) {
        if (clickDebounce()) {
            AudioPlayer.startActivity(requireContext(), it)
        }
    }
    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TracksFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.rvFavorite.adapter = adapter
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fillData()
    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Content -> showContent(state.tracks)
            is FavoriteState.Empty -> showEmpty()
            is FavoriteState.Loading -> showLoading()
        }
    }

    private fun showLoading() = with(binding) {
        this?.progressBar?.visibility = View.VISIBLE
        this?.messageText?.visibility = View.GONE
        this?.rvFavorite?.visibility = View.GONE
    }
    private fun showEmpty() {
        binding!!.progressBar.visibility = View.GONE
        binding!!.messageText.visibility = View.VISIBLE
        binding!!.rvFavorite.visibility = View.GONE
    }

    private fun showContent(trackList: List<Track>) {
        binding!!.progressBar.visibility = View.GONE
        binding!!.messageText.visibility = View.GONE
        binding!!.rvFavorite.visibility = View.VISIBLE
        tracks.clear()
        tracks.addAll(trackList)
        adapter.notifyDataSetChanged()
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
