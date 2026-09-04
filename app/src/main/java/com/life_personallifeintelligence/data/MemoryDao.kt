package com.life_personallifeintelligence.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoryDao {

    @Insert
    suspend fun insertMemory(memory: MemoryEntity)

    @Query("SELECT * FROM memories ORDER BY id DESC")
    suspend fun getAllMemories(): List<MemoryEntity>

    @Query("UPDATE memories SET isDone = :isDone WHERE id = :memoryId")
    suspend fun updateMemoryStatus(
        memoryId: Int,
        isDone: Boolean
    )

    @Query("DELETE FROM memories WHERE id = :memoryId")
    suspend fun deleteMemory(memoryId: Int)
    @Query("""
    UPDATE memories
    SET text = :text,
        category = :category,
        priority = :priority
    WHERE id = :memoryId
""")
    suspend fun updateMemory(
        memoryId: Int,
        text: String,
        category: String,
        priority: String
    )
}