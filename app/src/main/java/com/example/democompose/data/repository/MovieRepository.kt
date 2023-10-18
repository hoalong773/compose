package com.example.democompose.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.democompose.data.model.BaseModel
import com.example.democompose.data.model.Genres
import com.example.democompose.data.model.MovieItem
import com.example.democompose.data.model.paging.NowPlayingPagingDataSource
import com.example.democompose.data.remote.ApiService
import com.example.democompose.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) : MovieRepositoryInterface {

    override suspend fun search(searchKey: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.search(searchKey)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun genreList(): Flow<DataState<Genres>> = flow {
        emit(DataState.Loading)
        try {
            val genreResult = apiService.genreList()
            emit(DataState.Success(genreResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override fun nowPlayingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
        pagingSourceFactory = { NowPlayingPagingDataSource(apiService, genreId) },
        config = PagingConfig(pageSize = 1)
    ).flow

}