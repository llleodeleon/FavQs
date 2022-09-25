package com.leodeleon.favqs.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.leodeleon.favqs.data.IQuotesRepo
import com.leodeleon.favqs.data.QuotePageSource
import com.leodeleon.favqs.data.QuotesRepo
import com.leodeleon.favqs.data.model.Filter
import com.leodeleon.favqs.data.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val quotesRepo: IQuotesRepo,
): ViewModel() {

    val randomQuote: LiveData<Quote> = liveData {
        quotesRepo.getQotd().getOrNull()?.let {
            emit(it)
        }
    }

    private val _quotes = mutableStateOf<Flow<PagingData<Quote>>>(emptyFlow())
    val quotes: State<Flow<PagingData<Quote>>> = _quotes

    fun getQuotes(filter: Filter? = null) {
        _quotes.value = quotesRepo.quotesFlow(filter).flow.cachedIn(viewModelScope)
    }
}