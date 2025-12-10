package rs.example.playlistmaker.player.ui

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.SmallPlaylistItemBinding
import rs.example.playlistmaker.library.domain.model.PlayList
import java.io.File

class SmallPlayListViewHolder(
    private val binding: SmallPlaylistItemBinding,
    private val filePath: File
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlayList) {
        binding.plName.text = item.name
        val addedText = itemView.resources.getQuantityString(
            R.plurals.tracksContOfList,
            item.trackCount.toInt(), item.trackCount
        )
        binding.plCount.text = addedText
        val file = File(filePath, "${item.id}.jpg")
        Glide.with(itemView)
            .load(file.toUri().toString())
            .placeholder(R.drawable.ic_track_default)
            .transform(
                CenterCrop(),
                RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.crt_2))
            )
            .into(binding.plImage)
    }
}