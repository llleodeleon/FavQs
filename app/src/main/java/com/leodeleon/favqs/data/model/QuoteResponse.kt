package com.leodeleon.favqs.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuoteResponse(val quote: Quote)