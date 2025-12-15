package rs.example.playlistmaker.library.ui.view_holder


import android.annotation.SuppressLint
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import rs.example.playlistmaker.R
import rs.example.playlistmaker.databinding.PlaylistItemBinding
import rs.example.playlistmaker.library.domain.model.PlayList
import java.io.File


class PlayListsViewHolder(private val binding: PlaylistItemBinding, private val filePath: File) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(item: PlayList) {
        binding.plName.text = item.name
        val addedText = itemView.resources.getQuantityString(
            R.plurals.tracksContOfList,
            item.trackCount.toInt(), item.trackCount.toInt()
        )
        binding.plCount.text = addedText
        val file = File(filePath, "${item.id}.jpg")
        Glide.with(itemView)
            .load(if (file.exists()) file.toUri().toString() else "")
            .placeholder(R.drawable.ic_track_default)
            .transform(
                CenterCrop(),
                RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.i_p_8))
            )
            .into(binding.plImage)
    }

}
