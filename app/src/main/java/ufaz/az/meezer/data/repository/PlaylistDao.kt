package ufaz.az.meezer.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ufaz.az.meezer.data.model.Playlist
import ufaz.az.meezer.data.model.PlaylistTrack

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlists ORDER BY createdAt DESC")
    fun getAllPlaylists(): Flow<List<Playlist>>

    @Query("SELECT * FROM playlist_tracks WHERE playlistId = :playlistId ORDER BY addedAt DESC")
    fun getPlaylistTracks(playlistId: Long): Flow<List<PlaylistTrack>>

    @Insert
    suspend fun createPlaylist(playlist: Playlist): Long

    @Insert
    suspend fun addTrackToPlaylist(playlistTrack: PlaylistTrack)

    @Delete
    suspend fun deletePlaylist(playlist: Playlist)

    @Delete
    suspend fun removeTrackFromPlaylist(playlistTrack: PlaylistTrack)
}
