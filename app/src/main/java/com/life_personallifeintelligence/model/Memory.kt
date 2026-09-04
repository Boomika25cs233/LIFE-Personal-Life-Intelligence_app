package com.life_personallifeintelligence.model

data class Memory(
    val id: Int = 0,
    val text: String,
    val category: String = "General",
    val priority: String = "Normal",
    val isDone: Boolean = false
)