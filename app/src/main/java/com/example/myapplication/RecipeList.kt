package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Recipe(
    val name: String,
    val nutritionalScore: Int,
    val description: String
)

@Composable
fun RecipeList(innerPadding: PaddingValues) {
    val recipes = listOf(
        Recipe("Spaghetti Carbonara", 75, "Classic Italian pasta dish"),
        Recipe("Grilled Chicken Salad", 90, "Healthy and light"),
        Recipe("Beef Stir Fry", 80, "Quick and nutritious"),
        Recipe("Vegetable Curry", 85, "Flavorful Indian dish"),
        Recipe("Salmon with Rice", 95, "Omega-3 rich meal"),
        Recipe("Quinoa Bowl", 88, "High protein vegetarian option"),
        Recipe("Turkey Sandwich", 70, "Quick lunch option"),
        Recipe("Greek Salad", 92, "Mediterranean favorite"),
        Recipe("Chicken Soup", 78, "Comforting classic"),
        Recipe("Tofu Stir Fry", 87, "Vegetarian delight")
    )

    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    var selectedRecipes by remember { 
        mutableStateOf(mutableMapOf<String, Recipe>()) 
    }
    var selectedDay by remember { mutableStateOf<String?>(null) }
    var showRecipeGrid by remember { mutableStateOf<String?>(null) }

    if (showRecipeGrid != null) {
        RecipeGrid(
            recipes = recipes,
            onRecipeSelected = { recipe ->
                selectedRecipes = selectedRecipes.toMutableMap().apply {
                    put(showRecipeGrid!!, recipe)
                }
                showRecipeGrid = null
            },
            onBackClick = { showRecipeGrid = null }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Weekly Meal Planner",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(daysOfWeek) { day ->
                    DayRecipeCard(
                        day = day,
                        recipes = recipes,
                        selectedRecipe = selectedRecipes[day],
                        isSelected = selectedDay == day,
                        onDaySelected = { selectedDay = if (selectedDay == day) null else day },
                        onRecipeSelected = { recipe ->
                            selectedRecipes = selectedRecipes.toMutableMap().apply {
                                put(day, recipe)
                            }
                        },
                        onChangeRecipeClick = { showRecipeGrid = day }
                    )
                }
            }
        }
    }
}
