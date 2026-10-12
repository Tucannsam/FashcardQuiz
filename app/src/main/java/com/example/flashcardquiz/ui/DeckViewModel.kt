package com.example.flashcardquiz.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardquiz.data.AppDatabase
import com.example.flashcardquiz.data.Deck
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DeckViewModel(application: Application) : AndroidViewModel(application) {
    private val deckDao = AppDatabase.getInstance(application).deckDao()

    val decks: StateFlow<List<Deck>> = deckDao.getAllDecks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addDeck(name: String) {
        viewModelScope.launch { deckDao.insert(Deck(name = name)) }
    }

    fun deleteDeck(deck: Deck) {
        viewModelScope.launch { deckDao.delete(deck) }
    }
}