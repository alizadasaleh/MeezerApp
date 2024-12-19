package ufaz.az.meezer.data.api

data class TrackDetailsResponse(
    val id: Int?,
    val title: String,
    val title_short: String?,
    val title_version: String?,
    val unseen: Boolean?,
    val isrc: String?,
    val link: String?,
    val share: String?,
    val duration: Int?,
    val track_position: Int?,
    val disk_number: Int?,
    val rank: Int?,
    val release_date: String?,
    val explicit_lyrics: Boolean?,
    val explicit_content_lyrics: Int?,
    val explicit_content_cover: Int?,
    val preview: String,
    val bpm: Float?,
    val gain: Float?,
    val available_countries: List<String>?,
    val alternative: TrackDetailsResponse?,
    val contributors: List<Contributor>?,
    val md5_image: String?,
    val track_token: String?,
    val artist: Artist,
    val album: Album
)

