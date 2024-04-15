package com.rizrmdhn.kankerdetection.utils

import com.rizrmdhn.kankerdetection.data.source.remote.response.ArticlesItem
import com.rizrmdhn.kankerdetection.domain.model.News
import com.rizrmdhn.kankerdetection.domain.model.Source

object DataMapper {
    fun mapResponseToDomain(input: List<ArticlesItem>): List<News> {
        val newsList = ArrayList<News>()
        input.map {
            val news = News(
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                publishedAt = it.publishedAt,
                author = it.author ?: "",
                content = it.content ?: "",
                source = Source(
                    id = it.source.name,
                    name = it.source.id ?: ""
                )
            )
            newsList.add(news)
        }
        return newsList
    }
}