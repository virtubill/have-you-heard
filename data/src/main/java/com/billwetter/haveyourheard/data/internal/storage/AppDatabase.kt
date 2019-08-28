package com.billwetter.haveyourheard.data.internal.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.billwetter.haveyourheard.data.model.Article

@Database(entities = arrayOf(Article::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}