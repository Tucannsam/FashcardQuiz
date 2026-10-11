package com.example.flashcardquiz.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface DeckDao {
    @Query("SELECT * FROM decks ORDER BY name")
    fun getAllDecks(): Flow<List<Deck>>

    @Insert
    suspend fun insert(deck: Deck): Long

    @Delete
    suspend fun delete(deck: Deck)
}