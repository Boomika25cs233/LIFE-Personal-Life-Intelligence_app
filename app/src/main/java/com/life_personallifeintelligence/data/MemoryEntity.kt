package com.life_personallifeintelligence.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class MemoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val text: String,

    val category: String = "General",

    val priority: String = "Normal",

    val isDone: Boolean = false
)