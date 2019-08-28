package com.billwetter.haveyourheard.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Article(
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String = "",
    @Embedded(prefix = "source_")
    val source: Source = Source(),
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    // since on unique id is provided
    // we are faking a unique id using the url hashcode
    // (not 100% guaranteed but will work for demo purposes)
    @PrimaryKey var localId: Long
) : Parcelable {
    init {
        localId = url.hashCode().toLong()
    }
}