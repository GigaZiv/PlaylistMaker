package rs.example.playlistmaker.library.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.PlaylistsFragmentBinding
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.library.ui.PlaylistsState
import rs.example.playlistmaker.library.ui.adapter.PlayListAdapter
import rs.example.playlistmaker.library.ui.view_model.PlaylistViewModel

class PlayListFragment : Fragment() {

    private val viewModel by viewModel<PlaylistViewModel>()

    companion object {
        fun newInstance() = PlayListFragment().apply {
        }
    }

    private var _binding: PlaylistsFragmentBinding? = null
    private val binding get() = _binding!!
    private val playlists = mutableListOf<PlayList>()
    private var adapter = PlayListAdapter(playlists)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPlaylist.adapter = adapter
        binding.rvPlaylist.layoutManager =
            GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        viewModel.fill()
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_libraryFragment_to_playlistCreatorFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fill()
    }

    private fun render(state: PlaylistsState) {
        when (state) {
            is PlaylistsState.Content -> showContent(state.items)
            is PlaylistsState.Empty -> showEmpty()
        }
    }

    private fun showEmpty() {
        binding.messageText.visibility = View.VISIBLE
        binding.rvPlaylist.visibility = View.GONE

    }

    private fun showContent(items: List<PlayList>) {
        binding.messageText.visibility = View.GONE
        binding.rvPlaylist.visibility = View.VISIBLE
        playlists.clear()
        playlists.addAll(items)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
