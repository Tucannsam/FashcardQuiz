package com.example.flashcardquiz.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.flashcardquiz.data.AppDatabase
import com.example.flashcardquiz.data.Card
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CardViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val deckId: Long = checkNotNull(savedStateHandle["deckId"])
    private val cardDao = AppDatabase.getInstance(application).cardDao()

    val cards: StateFlow<List<Card>> = cardDao.getCardsForDeck(deckId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addCard(front: String, back: String) {
        viewModelScope.launch {
            cardDao.insert(Card(deckId = deckId, front = front, back = back))
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch { cardDao.delete(card) }
    }
}