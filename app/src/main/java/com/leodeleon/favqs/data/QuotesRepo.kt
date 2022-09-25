package com.leodeleon.favqs.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.haroldadmin.cnradapter.NetworkResponse
import com.leodeleon.favqs.data.model.Filter
import com.leodeleon.favqs.data.model.Quote
import com.leodeleon.favqs.data.model.QuotesResponse
import com.leodeleon.favqs.data.model.SessionRequest

interface IQuotesRepo {
    suspend fun login(user: String, password: String): Result<Boolean>

    suspend fun getQuotes(page: Int, filter: Filter? = null): Result<QuotesResponse>

    fun quotesFlow(filter: Filter? = null): Pager<Int, Quote>

    suspend fun getQotd(): Result<Quote>
}

class QuotesRepo(private val api: Api, private val prefsRepo: IPrefsRepo) : IQuotesRepo {

    override suspend fun login(user: String, password: String): Result<Boolean> {
        val request = SessionRequest(SessionRequest.User(login = user, password = password))
        return when (val response = api.startSession(request)) {
            is NetworkResponse.Success -> {
                val token = response.body.token
                prefsRepo.setToken(token)
                Result.success(true)
            }
            else -> Result.failure(Exception("Login error"))
        }
    }

    override suspend fun getQuotes(page: Int, filter: Filter?): Result<QuotesResponse> {
        return when (val response = api.getQuotes(page, filter?.filter, filter?.type?.value)) {
            is NetworkResponse.Success -> Result.success(response.body)
            is NetworkResponse.Error -> Result.failure(Exception("Error fetching quotes for page $page. ${response.error?.message}"))
        }
    }

    override fun quotesFlow(filter: Filter?): Pager<Int, Quote> {
        return Pager(PagingConfig(pageSize = QuotePageSource.PAGE_SIZE, enablePlaceholders = false)) {
            QuotePageSource(this, filter)
        }
    }

    override suspend fun getQotd(): Result<Quote> {
        return when (val response = api.getQotd()) {
            is NetworkResponse.Success -> Result.success(response.body.quote)
            else -> Result.failure(Exception("Error fetching quoted"))
        }
    }
}