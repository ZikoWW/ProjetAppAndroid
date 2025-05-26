package com.example.apptvseries.network

import com.example.apptvseries.model.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowApiService {
    @GET("most-popular")
    suspend fun getMostPopularTvShows(@Query("page") page: Int): TvShowResponse
}