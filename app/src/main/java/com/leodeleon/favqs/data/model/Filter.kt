package com.leodeleon.favqs.data.model

data class Filter(val filter: String, val type: Type? = null) {
    enum class Type(val value: String) {
        Tag("tag")
    }
}
