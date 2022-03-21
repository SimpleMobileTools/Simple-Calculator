package com.simplemobiletools.calculator.interfaces

import androidx.room.*
import com.simplemobiletools.calculator.models.History

@Dao
interface CalculatorDao {
    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT :limit")
    fun getHistory(limit: Int = 20): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(history: History): Long

    @Query("DELETE FROM history")
    fun deleteHistory()
}
