package com.billwetter.haveyourheard.data.internal.storage

import androidx.room.*
import com.billwetter.haveyourheard.data.model.Article
import io.reactivex.Single

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article ORDER BY publishedAt DESC")
    fun getAll(): Single<List<Article>>

    @Query("SELECT * FROM article ORDER BY publishedAt DESC LIMIT :offset, 20")
    fun get(offset: Int): Single<List<Article>>

    @Query("SELECT * FROM article WHERE localId = :localId")
    fun loadById(localId: Long): Single<Article>

    @Query("SELECT * FROM article WHERE bookmarked = 1")
    fun getBookmarked(): Single<List<Article>>

    @Insert
    fun insert(article: Article): Single<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(articles: List<Article>) : Single<List<Long>>

    @Update
    fun update(article: Article): Single<Int>

    @Delete
    fun delete(article: Article) : Single<Int>
}