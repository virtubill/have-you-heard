package com.billwetter.haveyourheard.data.model

data class ArticlesResponse(
    val articles: List<Article> = listOf(),
    val status: String = "",
    val totalResults: Int = 0
)