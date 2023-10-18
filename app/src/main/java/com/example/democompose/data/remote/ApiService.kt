package com.example.democompose.data.remote

import com.example.democompose.data.model.BaseModel
import com.example.democompose.data.model.Genres
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun nowPlayingMovieList(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String?,
        @Query("api_key") apiKey: String = ApiURL.API_KEY
    ): BaseModel

    @GET("search/movie?page=1&include_adult=false")
    suspend fun search(
        @Query("query") searchKey: String, @Query("api_key") apiKey: String = ApiURL.API_KEY
    ): BaseModel

    @GET("genre/movie/list")
    suspend fun genreList(@Query("api_key") apiKey: String = ApiURL.API_KEY): Genres

    @GET("discover/movie")
    suspend fun moviesByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: String,
        @Query("api_key") apiKey: String = ApiURL.API_KEY
    ): BaseModel
}