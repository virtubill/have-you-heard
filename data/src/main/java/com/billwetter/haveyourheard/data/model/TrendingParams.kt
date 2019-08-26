package com.billwetter.haveyourheard.data.model

class TrendingParams (country: String? = null, category: String? = null, pageSize: Int = 20, page: Int = 0) : HashMap<String, String>() {
    init {
        country?.let {  put("country", it) }
        category?.let {  put("category", it) }
        put("pageSize", pageSize.toString())
        put("page", page.toString())
    }
}