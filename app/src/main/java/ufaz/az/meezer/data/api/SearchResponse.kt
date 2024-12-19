package ufaz.az.meezer.data.api

data class SearchResponse(
    val data: List<SearchResult>
)

data class SearchResult(
    val id: String,
    val title: String,
    val artist: Artist,
    val album: Album,
    val preview: String?,
    val duration: Int?,
    val release_date: String?,
    val explicit_lyrics: Boolean?,
    val link: String?
)

