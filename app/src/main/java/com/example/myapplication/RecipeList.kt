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
        Recipe("Espaguetis Carbonara", 75, "Plato clásico italiano"),
        Recipe("Ensalada de Pollo a la Parrilla", 90, "Saludable y ligero"),
        Recipe("Salteado de Res", 80, "Rápido y nutritivo"),
        Recipe("Curry de Verduras", 85, "Sabroso plato indio"),
        Recipe("Salmón con Arroz", 95, "Rica en omega-3"),
        Recipe("Tazón de Quinoa", 88, "Opción vegetariana alta en proteínas"),
        Recipe("Sándwich de Pavo", 70, "Opción rápida para el almuerzo"),
        Recipe("Ensalada Griega", 92, "Favorito mediterráneo"),
        Recipe("Sopa de Pollo", 78, "Clásico reconfortante"),
        Recipe("Salteado de Tofu", 87, "Delicia vegetariana")
    )

    val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
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
                text = "Planificador Semanal de Comidas",
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
