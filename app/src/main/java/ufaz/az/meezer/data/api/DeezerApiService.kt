package ufaz.az.meezer.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {

    @GET("search")
    suspend fun search(
        @Query("q") query: String
    ): SearchResponse

    @GET("track/{trackId}")
    suspend fun getTrackDetails(@Path("trackId") trackId: String): TrackDetailsResponse
}


