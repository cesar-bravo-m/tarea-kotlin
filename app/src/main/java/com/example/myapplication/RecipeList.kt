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
    val description: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fiber: Int,
    val sodium: Int,
    val image: Int
)

@Composable
fun RecipeList(innerPadding: PaddingValues) {
    val recipes = listOf(
        Recipe(
            name = "Tacos de Pollo",
            nutritionalScore = 85,
            description = "Deliciosos tacos de pollo con especias mexicanas.",
            ingredients = listOf("Tortillas", "Pollo", "Cebolla", "Tomate", "Cilantro", "Especias"),
            instructions = listOf(
                "Corta el pollo en tiras.",
                "Sazona con especias mexicanas y cocina hasta que esté dorado.",
                "Calienta las tortillas.",
                "Sirve el pollo en las tortillas con cebolla, tomate y cilantro."
            ),
            calories = 300,
            protein = 25,
            carbs = 35,
            fiber = 5,
            sodium = 600,
            image = R.drawable.tacos_pollo
        ),
        Recipe(
            name = "Paella de Mariscos",
            nutritionalScore = 90,
            description = "Paella tradicional española con mariscos frescos.",
            ingredients = listOf(
                "Arroz",
                "Camarones",
                "Mejillones",
                "Calamares",
                "Pimiento",
                "Azafrán"
            ),
            instructions = listOf(
                "Sofríe los pimientos y el ajo.",
                "Añade el arroz y el caldo con azafrán.",
                "Incorpora los mariscos y cocina hasta que estén tiernos.",
                "Deja reposar y sirve caliente."
            ),
            calories = 400,
            protein = 30,
            carbs = 50,
            fiber = 3,
            sodium = 800,
            image = R.drawable.paella_mariscos
        ),
        Recipe(
            name = "Gazpacho Andaluz",
            nutritionalScore = 75,
            description = "Sopa fría refrescante hecha con vegetales frescos.",
            ingredients = listOf(
                "Tomates",
                "Pepino",
                "Pimiento",
                "Ajo",
                "Aceite de oliva",
                "Vinagre"
            ),
            instructions = listOf(
                "Pela y trocea los vegetales.",
                "Licua todos los ingredientes hasta obtener una textura suave.",
                "Refrigera por al menos 2 horas antes de servir.",
                "Sirve frío y disfruta."
            ),
            calories = 150,
            protein = 2,
            carbs = 12,
            fiber = 4,
            sodium = 300,
            image = R.drawable.gazpacho
        ),
        Recipe(
            name = "Tortilla Española",
            nutritionalScore = 80,
            description = "Clásica tortilla española con papas y huevos.",
            ingredients = listOf("Huevos", "Papas", "Cebolla", "Aceite de oliva", "Sal"),
            instructions = listOf(
                "Pela y corta las papas y la cebolla.",
                "Fríe las papas y la cebolla hasta que estén tiernas.",
                "Mezcla con los huevos batidos y cocina en una sartén.",
                "Dale la vuelta y cocina por ambos lados."
            ),
            calories = 250,
            protein = 10,
            carbs = 20,
            fiber = 2,
            sodium = 400,
            image = R.drawable.tortilla
        ),
        Recipe(
            name = "Empanadas de Carne",
            nutritionalScore = 70,
            description = "Empanadas rellenas de carne molida sazonada.",
            ingredients = listOf(
                "Harina",
                "Carne molida",
                "Cebolla",
                "Aceitunas",
                "Huevo",
                "Especias"
            ),
            instructions = listOf(
                "Prepara la masa con harina y agua.",
                "Cocina la carne con cebolla, aceitunas y especias.",
                "Rellena la masa con la mezcla de carne y ciérrala en forma de empanada.",
                "Hornea hasta que estén doradas."
            ),
            calories = 300,
            protein = 15,
            carbs = 25,
            fiber = 3,
            sodium = 500,
            image = R.drawable.empanadas
        ),
        Recipe(
            name = "Churros con Chocolate",
            nutritionalScore = 65,
            description = "Postre dulce y crujiente con chocolate caliente.",
            ingredients = listOf("Harina", "Azúcar", "Aceite", "Chocolate", "Leche"),
            instructions = listOf(
                "Prepara la masa con harina, agua y azúcar.",
                "Fríe en forma de churros hasta que estén dorados.",
                "Derrite el chocolate con leche.",
                "Sirve los churros con el chocolate caliente."
            ),
            calories = 450,
            protein = 5,
            carbs = 60,
            fiber = 2,
            sodium = 200,
            image = R.drawable.churros
        ),
        Recipe(
            name = "Ceviche de Pescado",
            nutritionalScore = 85,
            description = "Ceviche fresco con pescado marinado en limón.",
            ingredients = listOf("Pescado blanco", "Limón", "Cebolla", "Cilantro", "Ají", "Sal"),
            instructions = listOf(
                "Corta el pescado en trozos pequeños.",
                "Marina con jugo de limón y sal por 30 minutos.",
                "Añade cebolla, cilantro y ají.",
                "Sirve fresco acompañado de tostadas."
            ),
            calories = 200,
            protein = 20,
            carbs = 5,
            fiber = 1,
            sodium = 400,
            image = R.drawable.paella_mariscos
        ),
        Recipe(
            name = "Arepas Rellenas",
            nutritionalScore = 75,
            description = "Arepas venezolanas rellenas de queso y carne.",
            ingredients = listOf("Harina de maíz", "Queso", "Carne mechada", "Sal", "Aceite"),
            instructions = listOf(
                "Prepara la masa con harina de maíz y agua.",
                "Forma las arepas y cocínalas hasta que estén doradas.",
                "Abre y rellena con queso y carne mechada.",
                "Sirve caliente."
            ),
            calories = 350,
            protein = 15,
            carbs = 30,
            fiber = 3,
            sodium = 500,
            image = R.drawable.empanadas
        ),
        Recipe(
            name = "Ensalada Mediterránea",
            nutritionalScore = 90,
            description = "Ensalada saludable con ingredientes frescos.",
            ingredients = listOf(
                "Lechuga",
                "Tomate",
                "Pepino",
                "Aceitunas",
                "Queso feta",
                "Aceite de oliva"
            ),
            instructions = listOf(
                "Lava y corta todos los vegetales.",
                "Mezcla con aceitunas y queso feta.",
                "Aliña con aceite de oliva y sal.",
                "Sirve fría."
            ),
            calories = 180,
            protein = 5,
            carbs = 10,
            fiber = 3,
            sodium = 300,
            image = R.drawable.ensalada
        ),
        Recipe(
            name = "Flan Casero",
            nutritionalScore = 70,
            description = "Postre clásico de flan con caramelo.",
            ingredients = listOf("Leche", "Huevos", "Azúcar", "Vainilla"),
            instructions = listOf(
                "Prepara el caramelo y viértelo en moldes.",
                "Mezcla leche, huevos, azúcar y vainilla.",
                "Vierte la mezcla en los moldes con caramelo.",
                "Hornea a baño maría hasta que esté firme."
            ),
            calories = 250,
            protein = 7,
            carbs = 30,
            fiber = 0,
            sodium = 150,
            image = R.drawable.flan
        )
    )

    val daysOfWeek =
        listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
    var selectedRecipes by remember {
        mutableStateOf(mutableMapOf<String, Recipe>())
    }
    var selectedDay by remember { mutableStateOf<String?>(null) }
    var showRecipeGrid by remember { mutableStateOf<String?>(null) }
    val (showRecipeDetail, setShowRecipeDetail) = remember { mutableStateOf(false) }
    val (selectedRecipeDetail, setSelectedRecipeDetail) = remember { mutableStateOf<Recipe?>(null) }

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
    } else if (showRecipeDetail && selectedRecipeDetail != null) {
        RecipeDetail(
            recipe = selectedRecipeDetail,
            onBackClick = {
                setSelectedRecipeDetail(null)
                setShowRecipeDetail(false)
            },
            onSelectRecipe = {
                setSelectedRecipeDetail(null)
                setShowRecipeDetail(false)
            }
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
                        onChangeRecipeClick = { showRecipeGrid = day },
                        setShowRecipeDetail = setShowRecipeDetail,
                        setSelectedRecipeDetail = setSelectedRecipeDetail
                    )
                }
            }
        }
    }
}
