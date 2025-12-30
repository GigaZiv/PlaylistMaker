package rs.example.playlistmaker.search.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.TrackItemBinding
import rs.example.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class SearchViewHolder(private val binding: TrackItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Track) {
        binding.twNameSong.text = item.trackName
        binding.twNameGroup.text = item.artistName
        binding.twDuration.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(item.trackTimeMillis)
        Glide.with(itemView)
            .load(item.artworkUrl100.replaceAfterLast('/', "60x60bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(
                itemView.resources.getDimensionPixelSize(R.dimen.crt_2)))
            .into(binding.ivTrackImage)
    }
}
