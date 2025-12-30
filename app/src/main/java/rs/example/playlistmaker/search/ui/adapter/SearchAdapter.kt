package rs.example.playlistmaker.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.example.playlistmaker.databinding.TrackItemBinding
import rs.example.playlistmaker.search.domain.models.Track
import rs.example.playlistmaker.search.ui.viewholder.SearchViewHolder


class SearchAdapter(
    private val data: List<Track>,
    private val clickListener: (Track) -> Unit
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
    }

    override fun getItemCount(): Int {
        return data.size
    }
}