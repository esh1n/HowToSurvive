package com.esh1n.guidtoarchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
class ArticleEntry(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon_id")
    val content: String,
    @ColumnInfo(name = "categoryName")
    val categoryName: String
)

