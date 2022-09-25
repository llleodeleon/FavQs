package com.leodeleon.favqs.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionRequest(
    val user: User
) {
    @JsonClass(generateAdapter = true)
    data class User(val login: String, val password: String)
}

@JsonClass(generateAdapter = true)
data class SessionResponse(
    @Json(name = "User-Token")
    val token: String
)
