package com.leodeleon.favqs.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Quote(
    val id: Int,
    val body: String?,
    val author: String?,
    val tags: List<String>?
)