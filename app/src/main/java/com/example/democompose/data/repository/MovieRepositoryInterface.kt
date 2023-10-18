package com.example.democompose.data.repository

import androidx.paging.PagingData
import com.example.democompose.data.model.BaseModel
import com.example.democompose.data.model.Genres
import com.example.democompose.data.model.MovieItem
import com.example.democompose.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    suspend fun search(searchKey: String): Flow<DataState<BaseModel>>
    suspend fun genreList(): Flow<DataState<Genres>>
    fun nowPlayingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>>
}