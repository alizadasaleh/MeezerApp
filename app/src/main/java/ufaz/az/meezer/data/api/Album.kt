package ufaz.az.meezer.data.api

data class Album(
    val id: Int?,
    val title: String,
    val link: String?,
    val cover: String?,
    val cover_small: String?,
    val cover_medium: String?,
    val cover_big: String?,
    val cover_xl: String?,
    val release_date: String?
)

data class Contributor(
    val id: Int?,
    val name: String?,
    val role: String?
)