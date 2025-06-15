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
import rs.example.playlistmaker.adapter.SearchAdapter.TrackHolder
import rs.example.playlistmaker.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class SearchAdapter(
    private val itemsTrack: ArrayList<Track>
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

    inner class TrackHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val itemTrackImage: ImageView by lazy { v.findViewById(R.id.ivTrackImage) }
        private val itemNameSong: TextView by lazy { v.findViewById(R.id.twNameSong) }
        private val itemNameGroup: TextView by lazy { v.findViewById(R.id.twNameGroup) }
        private val itemDuration: TextView by lazy { v.findViewById(R.id.twDuration) }

        fun bind(i: Track) {
            itemNameSong.text = i.trackName
            itemNameGroup.text = i.artistName
            itemDuration.text = i.trackTime

            Glide.with(itemView)
                .load(i.artworkUrl100)
                .centerCrop()
                .placeholder(R.drawable.ic_track_default)
                .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.crt_2)))
                .into(itemTrackImage)
        }
    }
}

