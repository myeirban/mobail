package com.example.examlastiinlast.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mongolian: String,
    val foreign: String
)