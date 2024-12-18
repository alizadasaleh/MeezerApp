package ufaz.az.meezer.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String
    ): SearchResponse
}
