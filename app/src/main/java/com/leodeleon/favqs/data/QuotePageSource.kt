package com.leodeleon.favqs.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.leodeleon.favqs.data.model.Filter
import com.leodeleon.favqs.data.model.Quote

private const val STARTING_PAGE_INDEX = 1
class QuotePageSource(
    private val quotesRepo: QuotesRepo,
    private val filter: Filter? = null,
) : PagingSource<Int, Quote>() {
    companion object {
        const val PAGE_SIZE = 25
    }

    override fun getRefreshKey(state: PagingState<Int, Quote>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return quotesRepo.getQuotes(position, filter).fold(
            onSuccess = { response ->
                val noResults = response.quotes.size == 1 && response.quotes.first().id == 0
                LoadResult.Page(
                    data = response.quotes,
                    prevKey = if (position == STARTING_PAGE_INDEX) null else response.page - 1,
                    nextKey = if (response.lastPage or noResults) null else response.page + 1
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }
}