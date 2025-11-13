package rs.example.playlistmaker.library.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.FragmentLibraryBinding
import rs.example.playlistmaker.library.ui.adapter.LibraryViewPagerAdapter

class LibraryFragment : Fragment() {

    private var binding: FragmentLibraryBinding? = null
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding!!.viewPager.adapter = LibraryViewPagerAdapter(childFragmentManager, lifecycle)

        tabMediator = TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = requireContext().getString(R.string.my_track)
                1 -> tab.text = requireContext().getString(R.string.playlists)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
