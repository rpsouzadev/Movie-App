package com.rpsouza.movieapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rpsouza.movieapp.BuildConfig
import com.rpsouza.movieapp.data.api.ServiceAPI
import com.rpsouza.movieapp.data.remote.model.movie.MovieResponse
import com.rpsouza.movieapp.utils.Constants

class SearchMoviePagingSource(
    private val serviceAPI: ServiceAPI,
    private val query: String
) : PagingSource<Int, MovieResponse>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, MovieResponse> {
        return try {
            val page = params.key ?: Constants.Paging.DEFAULT_PAGE_INDEX
            val response = serviceAPI.searchMovies(
                apiKey = BuildConfig.THE_MOVIE_DB_KEY,
                language = Constants.Movie.LANGUAGE,
                query = query,
                page = page
            ).results ?: emptyList()

            LoadResult.Page(
                data = response,
                prevKey = if (page == Constants.Paging.DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}