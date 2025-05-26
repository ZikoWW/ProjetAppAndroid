package com.example.apptvseries.repository

import com.example.apptvseries.model.TvShow
import com.example.apptvseries.network.TvShowApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface TvShowRepository {
    suspend fun getMostPopularTvShows(page: Int): Result<List<TvShow>>
    suspend fun searchTvShows(query: String): Result<List<TvShow>>
}

class TvShowRepositoryImpl : TvShowRepository {

    private val apiService: TvShowApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://www.episodate.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvShowApiService::class.java)
    }

    override suspend fun getMostPopularTvShows(page: Int): Result<List<TvShow>> {
        return try {
            val response = apiService.getMostPopularTvShows(page)
            Result.success(response.tvShows)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchTvShows(query: String): Result<List<TvShow>> {
        return try {
            val response = apiService.searchTvShows(query)
            Result.success(response.tvShows)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}