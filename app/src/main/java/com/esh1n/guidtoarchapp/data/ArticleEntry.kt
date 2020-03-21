package com.esh1n.guidtoarchapp.data

import androidx.room.*


@Entity(
    tableName = "article_table",
    foreignKeys = [ForeignKey(
        entity = CategoryEntry::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("categoryName"), onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["categoryName"])]
)
class ArticleEntry(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon_id")
    val content: String,
    @ColumnInfo(name = "categoryName")
    val categoryName: String,
    @ColumnInfo(name = "isSaved")
    val isSaved: Boolean

)

