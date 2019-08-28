package com.billwetter.haveyourheard.data.internal.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.billwetter.haveyourheard.data.model.Article
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article ORDER BY publishedAt DESC")
    fun getAll(): Single<List<Article>>

    @Query("SELECT * FROM article WHERE localId = :localId")
    fun loadById(localId: Long): Single<Article>

    @Insert
    fun insert(article: Article): Single<Long>

    @Delete
    fun delete(article: Article) : Single<Int>
}