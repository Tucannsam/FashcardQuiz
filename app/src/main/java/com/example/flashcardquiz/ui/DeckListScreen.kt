package com.example.flashcardquiz.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckListScreen(onDeckClick: (Long) -> Unit, viewModel: DeckViewModel = viewModel()) {
    val decks by viewModel.decks.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("MyDecks")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true}) {
                Icon(Icons.Default.Add, contentDescription = "Add Deck")
            }
        }
    ) { padding ->
        if (decks.isEmpty()) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No decks yet. Tap + to add one")
            }
        } else {
            LazyColumn(Modifier.padding(padding)) {
                items(decks, key = { it.id }) { deck ->
                    ListItem(
                        modifier = Modifier.clickable { onDeckClick(deck.id) },
                        headlineContent = { Text(deck.name) },
                        trailingContent = {
                            IconButton(onClick = { viewModel.deleteDeck(deck) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete deck")
                            }
                        }
                    )
                    HorizontalDivider()
                }
            }

        }
    }

if (showDialog) {
    var name by remember {mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { showDialog = false },
        title = { Text("New Deck") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Deck name") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank()) {
                    viewModel.addDeck(name.trim())
                    showDialog = false
                }
            }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }
}
