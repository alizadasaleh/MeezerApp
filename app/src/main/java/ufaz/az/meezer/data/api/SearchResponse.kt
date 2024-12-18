package ufaz.az.meezer.data.api

data class SearchResponse(
    val data: List<SearchResult>,
    val total: Int,
    val next: String?
)

data class SearchResult(
    val id: String,
    val title: String,
    val artist: Artist,
    val album: Album,
    val duration: Int
)

data class Artist(
    val id: String,
    val name: String
)

data class Album(
    val id: String,
    val title: String,
    val cover_medium: String
)
