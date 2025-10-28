package rs.example.playlistmaker.library.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.databinding.TracksFragmentBinding
import rs.example.playlistmaker.library.ui.viewmodel.TracksViewModel

class TracksFragment : Fragment() {

    private val viewModel by viewModel<TracksViewModel>()

    companion object {
        fun newInstance() = TracksFragment().apply {
        }
    }

    private var binding: TracksFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TracksFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }
}
