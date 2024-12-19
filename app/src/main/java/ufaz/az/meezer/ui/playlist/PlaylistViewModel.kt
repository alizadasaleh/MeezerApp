package ufaz.az.meezer.ui.playlist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import ufaz.az.meezer.data.api.SearchResult
import ufaz.az.meezer.data.model.Playlist
import ufaz.az.meezer.data.model.PlaylistTrack
import ufaz.az.meezer.data.repository.PlaylistDao

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val playlistDao: PlaylistDao
) : ViewModel() {
    val playlists = playlistDao.getAllPlaylists()
    private val _selectedPlaylistId = MutableStateFlow<Long?>(null)
    val selectedPlaylistTracks = _selectedPlaylistId.flatMapLatest { playlistId ->
        playlistId?.let { playlistDao.getPlaylistTracks(it) } ?: flowOf(emptyList())
    }

    suspend fun createPlaylist(name: String) {
        playlistDao.createPlaylist(Playlist(name = name))
    }

    suspend fun addTrackToPlaylist(playlistId: Long, searchResult: SearchResult) {
        playlistDao.addTrackToPlaylist(
            PlaylistTrack(
                playlistId = playlistId,
                trackId = searchResult.id,
                title = searchResult.title,
                artist = searchResult.artist.name,
                albumTitle = searchResult.album.title
            )
        )
    }

    fun selectPlaylist(playlistId: Long) {
        _selectedPlaylistId.value = playlistId
    }

    suspend fun deletePlaylist(playlist: Playlist) {
        playlistDao.deletePlaylist(playlist)
    }

    suspend fun removeTrackFromPlaylist(playlistTrack: PlaylistTrack) {
        playlistDao.removeTrackFromPlaylist(playlistTrack)
    }
}


