package rs.example.playlistmaker.player.ui

import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.example.playlistmaker.AppConstant.Companion.ALBUM
import rs.example.playlistmaker.databinding.SmallPlaylistItemBinding
import rs.example.playlistmaker.library.domain.model.PlayList
import java.io.File

class SmallPlayListAdapter(
    val data: List<PlayList>,
    private val clickListener: (PlayList) -> Unit
) :
    RecyclerView.Adapter<SmallPlayListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallPlayListViewHolder {
        val filePath =
            File(parent.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), ALBUM)
        val layoutInspector = LayoutInflater.from(parent.context)
        return SmallPlayListViewHolder(
            SmallPlaylistItemBinding.inflate(layoutInspector, parent, false),
            filePath
        )
    }

    override fun onBindViewHolder(holder: SmallPlayListViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
