package rs.example.playlistmaker.library.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.databinding.PlaylistsFragmentBinding
import rs.example.playlistmaker.library.ui.viewmodel.PlaylistViewModel

class PlayListFragment : Fragment() {

    private val viewModel by viewModel<PlaylistViewModel>()

    companion object {
        fun newInstance() = PlayListFragment().apply {
        }
    }

    private var binding: PlaylistsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlaylistsFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}
