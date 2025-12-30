package rs.example.playlistmaker.library.ui.adapter

import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rs.example.playlistmaker.AppConstant.Companion.ALBUM
import rs.example.playlistmaker.databinding.PlaylistItemBinding
import rs.example.playlistmaker.library.domain.model.PlayList
import rs.example.playlistmaker.library.ui.view_holder.PlayListsViewHolder
import java.io.File

class PlayListAdapter(
    private val data: List<PlayList>,
    private val clickListener: (PlayList) -> Unit
) :
    RecyclerView.Adapter<PlayListsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListsViewHolder {
        val filePath =
            File(parent.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), ALBUM)
        val layoutInspector = LayoutInflater.from(parent.context)
        return PlayListsViewHolder(
            PlaylistItemBinding.inflate(layoutInspector, parent, false),
            filePath
        )
    }

    override fun onBindViewHolder(holder: PlayListsViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
