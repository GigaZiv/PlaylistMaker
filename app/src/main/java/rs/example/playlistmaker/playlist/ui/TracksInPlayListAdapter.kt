package rs.example.playlistmaker.playlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.TrackItemBinding
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.search.ui.viewholder.SearchViewHolder

class TracksInPlayListAdapter(
    private val data: List<Track>,
    private val deleteAlertDialog: MaterialAlertDialogBuilder,
    private val clickListener: (Track) -> Unit,
    private val longClickListener: (Track) -> Unit
) :
    RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return SearchViewHolder(TrackItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(data[position])
        }
        holder.itemView.setOnLongClickListener {
            deleteAlertDialog
                .setNegativeButton(R.string.no_button) { _, _ ->
                }
                .setPositiveButton(R.string.yes_button) { _, _ ->
                    longClickListener.invoke(data[position])
                }
            deleteAlertDialog.show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}