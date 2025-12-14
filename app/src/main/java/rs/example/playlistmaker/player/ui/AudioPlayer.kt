package rs.example.playlistmaker.player.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import rs.example.playlistmaker.player.ui.view_model.PlayerViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.AudioPlayerBinding
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.main.ui.MainActivityViewModel
import rs.example.playlistmaker.player.util.PlayerState
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormatLong

class AudioPlayer : Fragment() {
    private val viewModel by viewModel<PlayerViewModel>()
    private val hostViewModel by activityViewModel<MainActivityViewModel>()
    private var _binding: AudioPlayerBinding? = null
    private val binding get() = _binding!!
    private val playlists = mutableListOf<PlayList>()
    var track: Track? = null
    private val adapter = SmallPlayListAdapter(playlists)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AudioPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hostViewModel.getCurrentTrack().observe(viewLifecycleOwner) { currentTrack ->
            this.track = currentTrack
            renderInformation(currentTrack)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.playlistsBottomSheet).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.isDraggable = layoutManager.findFirstCompletelyVisibleItemPosition() == 0
            } else {
                bottomSheetBehavior.isDraggable = true
            }
        }

        adapter.clickListener = {
            track?.let {
                trk -> viewModel.addToPlaylist(trk, it)
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }
                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                        viewModel.renderPlayLists()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        binding.recyclerView.adapter = adapter
        binding.ibPlay.setOnClickListener { viewModel.playbackControl() }
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.observeProgressTimeState().observe(viewLifecycleOwner) {
            progressTimeViewUpdate(it)
        }
        viewModel.observeFavoriteState().observe(viewLifecycleOwner) {
            favoriteRender(it)
        }
        viewModel.observePlaylistState().observe(viewLifecycleOwner) {
            renderPlayList(it)
        }

        viewModel.observeAddDate().observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED){
                showToast(it)
            }
        }
        binding.iwBack.setOnClickListener { findNavController().navigateUp() }

        binding.ibAddFavorite.setOnClickListener { track?.let { it1 -> viewModel.onFavoriteClicked(it1) } }
        binding.ibAddTrackMedia.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.addPlaylistButton.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            findNavController().navigate((R.id.action_audioPlayer_to_playlistCreatorFragment))
        }
    }

    private fun renderInformation(track: Track) {
        viewModel.prepare(track)
        binding.twTitleValue.text = track.trackName
        binding.twArtistValue.text = track.artistName
        binding.iwCollectionNameValue.text = track.collectionName
        binding.iwReleaseDateValue.text = track.releaseDate?.substring(0, 4)
        binding.iwGenresValue.text = track.primaryGenreName
        binding.iwCountryNameValue.text = track.country
        binding.iwTrackTimeMillisValue.text = getSimpleDateFormatLong(track.trackTimeMillis)
        Glide.with(requireActivity())
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.ic_track_default)
            .centerCrop()
            .transform(RoundedCorners(
                requireActivity().resources.getDimensionPixelSize(R.dimen.icp_8)))
            .into(binding.ivTrackImageLarge)
    }

    private fun renderPlayList(list: List<PlayList>) {
        playlists.clear()
        playlists.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun favoriteRender(favoriteChecked: Boolean) {
        if (favoriteChecked)
            binding.ibAddFavorite.setImageResource(R.drawable.ic_favorite_on)
        else binding.ibAddFavorite.setImageResource(R.drawable.ic_favorite_off)
    }

    private fun render(state: PlayerState) {
        when (state) {
            PlayerState.PLAYING -> startPlayer()
            PlayerState.PAUSED, PlayerState.PREPARED -> pausePlayer()
        }
    }

    private fun startPlayer() {
        binding.ibPlay.setImageResource(R.drawable.ic_pause_button)
    }

    private fun pausePlayer() {
        binding.ibPlay.setImageResource(R.drawable.ic_play_button)
    }

    private fun progressTimeViewUpdate(progressTime: String) {
        binding.twProgressTime.text = progressTime
    }

    private fun showToast(result: Pair<String, Boolean>) {
        if (result.second) {
            BottomSheetBehavior.from(binding.playlistsBottomSheet).apply {
                state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
        val message = if (result.second) getString(R.string.add_track_message) else getString(R.string.add_track_message_false)
        Toast.makeText(requireContext(), message + " " + result.first, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
   }
}
