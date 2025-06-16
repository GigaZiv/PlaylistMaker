package rs.example.playlistmaker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rs.example.playlistmaker.R
import rs.example.playlistmaker.adapter.TracksAdapter.TrackHolder
import rs.example.playlistmaker.models.Track

class TracksAdapter(
    private val itemsTrack: List<Track>
) : Adapter<TrackHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrackHolder {
        return TrackHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rw_search_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: TrackHolder,
        position: Int
    ) {
        holder.bind(itemsTrack[position])
    }

    override fun getItemCount(): Int {
        return itemsTrack.size
    }

    inner class TrackHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val itemTrackImage: ImageView by lazy { view.findViewById(R.id.ivTrackImage) }
        private val itemNameSong: TextView by lazy { view.findViewById(R.id.twNameSong) }
        private val itemNameGroup: TextView by lazy { view.findViewById(R.id.twNameGroup) }
        private val itemDuration: TextView by lazy { view.findViewById(R.id.twDuration) }

        fun bind(trackItem: Track) {
            itemNameSong.text = trackItem.trackName
            itemNameGroup.text = trackItem.artistName
            itemDuration.text = trackItem.trackTime

            Glide.with(itemView)
                .load(trackItem.artworkUrl100)
                .centerCrop()
                .placeholder(R.drawable.ic_track_default)
                .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.crt_2)))
                .into(itemTrackImage)
        }
    }
}

