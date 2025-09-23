package rs.example.playlistmaker.search.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.example.playlistmaker.databinding.ActivitySearchBinding
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.AppConstant.Companion.ID_SEARCH_QUERY
import rs.example.playlistmaker.AppConstant.Companion.CLICK_DEBOUNCE_DELAY
import rs.example.playlistmaker.R
import rs.example.playlistmaker.player.ui.AudioPlayer
import rs.example.playlistmaker.search.ui.adapter.SearchAdapter
import rs.example.playlistmaker.search.ui.view_model.SearchViewModel
class SearchActivity : AppCompatActivity() {
    private var isClickAllowed = true
    private val threadHandler by lazy {
        Handler(Looper.getMainLooper())
    }
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModel<SearchViewModel>()

    private val tracks: MutableList<Track> = mutableListOf()
    private val adapter = SearchAdapter(tracks) {
        if (clickDebounce()) {
            viewModel.setTrack(it)
            AudioPlayer.startActivity(this, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        viewModel.observeState().observe(this) {
            render(it)
        }

        binding.apply {

            intent.extras?.let {
                etSearch.setText(it.getString(ID_SEARCH_QUERY))
            }

            setSupportActionBar(tbSearch)

            tbSearch.setNavigationOnClickListener {
                hideKeyboard(); this@SearchActivity.finish()
            }

            iwClear.setOnClickListener {
                viewModel.searchDebounce("")
                etSearch.text.clear()
                hideKeyboard()
            }

            rcvSearch.adapter = adapter
            rcvHistory.adapter = adapter

            etSearch.apply {

                doAfterTextChanged { s ->
                    iwClear.isVisible = !s.isNullOrEmpty()
                    viewModel.searchDebounce(etSearch.text.trim().toString())
                }

                setOnFocusChangeListener { view, hasFocus ->
                    if (etSearch.text.isEmpty()) viewModel.searchHistory()
                }
            }

            bRefreshNetwork.setOnClickListener {
                viewModel.searchDebounce(etSearch.text.trim().toString(), true)

            }

            bClearHistory.setOnClickListener {
                viewModel.clear(); llHistory.isVisible = false
            }
        }

    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            threadHandler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun hideKeyboard() {
        this.currentFocus?.let { view ->
            val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun render(state: SearchState) {
        when (state) {
            is SearchState.Loading -> showLoading()
            is SearchState.Content -> showContent(state.tracks)
            is SearchState.Error -> showError(state.errorMessage)
            is SearchState.Empty -> showEmpty(state.message)
            is SearchState.EmptyInput -> showHistory(state.tracks)
            is SearchState.AllEmpty -> showEmptyHistory()
        }
    }


    private fun showHistory(trackList: List<Track>) = with(binding) {
        llTrackNotFound.visibility = View.GONE
        llHistory.visibility = View.VISIBLE
        rcvHistory.visibility = View.VISIBLE
        rcvSearch.visibility = View.GONE
        llNwNotFound.visibility = View.GONE
        pbProgress.visibility = View.GONE
        tracks.clear()
        tracks.addAll(trackList)
        adapter.notifyDataSetChanged()
    }

    private fun showEmptyHistory() = with(binding) {
        llTrackNotFound.visibility = View.GONE
        llHistory.visibility = View.GONE
        rcvHistory.visibility = View.GONE
        rcvSearch.visibility = View.GONE
        llNwNotFound.visibility = View.GONE
        pbProgress.visibility = View.GONE
    }

    private fun showLoading() = with(binding) {
        llTrackNotFound.visibility = View.GONE
        llHistory.visibility = View.GONE
        rcvHistory.visibility = View.GONE
        rcvSearch.visibility = View.GONE
        llNwNotFound.visibility = View.GONE
        pbProgress.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) = with(binding) {
        llTrackNotFound.visibility = View.GONE
        rcvHistory.visibility = View.GONE
        rcvSearch.visibility = View.GONE
        llNwNotFound.visibility = View.VISIBLE
        pbProgress.visibility = View.GONE
        twErrorNetwork.text = errorMessage
    }

    private fun showEmpty(emptyMessage: String) = with(binding) {
        rcvHistory.visibility = View.GONE
        rcvSearch.visibility = View.GONE
        llTrackNotFound.visibility = View.VISIBLE
        pbProgress.visibility = View.GONE
        twTrNotFound.text = emptyMessage
    }

    private fun showContent(trackList: List<Track>) = with(binding) {
        llTrackNotFound.visibility = View.GONE
        rcvHistory.visibility = View.GONE
        rcvSearch.visibility = View.VISIBLE
        llTrackNotFound.visibility = View.GONE
        pbProgress.visibility = View.GONE
        tracks.clear()
        tracks.addAll(trackList)
        adapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ID_SEARCH_QUERY, binding.etSearch.text.toString())
    }

    companion object {

        fun show(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)

        }
    }
}