package com.example.democompose.ui.screens.bottom_screen.nowplaying

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.democompose.data.model.Genre
import com.example.democompose.data.model.GenreId
import com.example.democompose.ui.component.MovieItemList
import com.example.democompose.ui.screens.bottomnavigation.nowplaying.NowPlayingViewModel

@Composable
fun NowPlaying(
    navController: NavController,
    genres: ArrayList<Genre>? = null,
) {
    val nowPlayViewModel = hiltViewModel<NowPlayingViewModel>()
    MovieItemList(
        navController = navController,
        movies = nowPlayViewModel.nowPlayingMovies,
        genres = genres,
        selectedName = nowPlayViewModel.selectedGenre.value
    ){
        nowPlayViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            nowPlayViewModel.selectedGenre.value = it
        }
    }
}