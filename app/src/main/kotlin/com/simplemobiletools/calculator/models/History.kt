package com.simplemobiletools.calculator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "history", indices = [(Index(value = ["id"], unique = true))])
data class History(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "formula") var formula: String,
    @ColumnInfo(name = "result") var result: String,
    @ColumnInfo(name = "timestamp") var timestamp: Long
)
