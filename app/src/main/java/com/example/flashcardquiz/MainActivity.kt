package com.example.flashcardquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flashcardquiz.ui.theme.FlashcardQuizTheme
import com.example.flashcardquiz.ui.DeckListScreen
import com.example.flashcardquiz.ui.CardListScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlashcardQuizTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "decks") {
                    composable("decks") {
                        DeckListScreen(
                            onDeckClick = { deckId -> navController.navigate("cards/$deckId") }
                        )
                    }
                    composable(
                        route = "cards/{deckId}",
                        arguments = listOf(navArgument("deckId") { type = NavType.LongType })
                    ) {
                        CardListScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
