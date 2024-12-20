package ufaz.az.meezer.ui.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ufaz.az.meezer.R
import ufaz.az.meezer.data.model.Playlist

class PlaylistAdapter(
    private val playlists: List<Playlist>,
    private val onPlaylistClickListener: (Playlist) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item_layout, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = playlists[position]
        holder.playlistNameTextView.text =
            playlist.name  // Assuming 'name' is a property in Playlist.
        holder.itemView.setOnClickListener { onPlaylistClickListener(playlist) }
    }

    override fun getItemCount(): Int = playlists.size

    class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playlistNameTextView: TextView = itemView.findViewById(R.id.playlist_name_text_view)
    }
}