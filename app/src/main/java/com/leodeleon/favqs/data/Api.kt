package com.leodeleon.favqs.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.leodeleon.favqs.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    @POST("session")
    suspend fun startSession(@Body body: SessionRequest): NetworkResponse<SessionResponse, ErrorResponse>

    @GET("quotes")
    suspend fun getQuotes(@Query("page") page: Int, @Query("filter") filter: String?, @Query("type") type: String?): NetworkResponse<QuotesResponse, ErrorResponse>

    @GET("qotd")
    suspend fun getQotd(): NetworkResponse<QuoteResponse, ErrorResponse>
}