package rs.example.playlistmaker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rs.example.playlistmaker.R
import rs.example.playlistmaker.adapter.TracksAdapter.TrackHolder
import rs.example.playlistmaker.databinding.RwSearchItemBinding
import rs.example.playlistmaker.models.Track
import rs.example.playlistmaker.utils.StaffFunctions.getSimpleDateFormat

class TracksAdapter(
    private val itemTracks: List<Track>,
    private val clickListener: (Track) -> Unit
) : Adapter<TrackHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackHolder {
        return TrackHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rw_search_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: TrackHolder,
        position: Int
    ) {
        holder.bind(itemTracks[position])
        holder.itemView.setOnClickListener { clickListener.invoke(itemTracks[position]) }
    }

    override fun getItemCount(): Int = itemTracks.size

    inner class TrackHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bindingHolderItem = RwSearchItemBinding.bind(view)
        fun bind(trackItem: Track) = with(bindingHolderItem) {
            twNameSong.text = trackItem.trackName
            twNameGroup.text = trackItem.artistName
            twDuration.text = getSimpleDateFormat(trackItem.trackTimeMillis)
            Glide.with(itemView)
                .load(trackItem.artworkUrl100)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .placeholder(R.drawable.ic_track_default)
                .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.crt_2)))
                .into(ivTrackImage)
        }
    }
}

