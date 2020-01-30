package com.esh1n.guidtoarchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
class CategoryEntry(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "icon_id")
    val iconId:String
)