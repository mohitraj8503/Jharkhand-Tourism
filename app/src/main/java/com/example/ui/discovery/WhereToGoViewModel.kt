package com.example.ui.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Destination
import com.example.data.repository.TruRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

enum class WhereToGoCategory(val title: String) {
    ALL("All"),
    WATERFALLS("Waterfalls"),
    SACRED("Sacred & Religious Sites"),
    HILL_STATIONS("Hill Stations"),
    WILDLIFE("Wildlife"),
    PARKS("Parks & Gardens"),
    ART_CULTURE("Art & Culture"),
    MONSOON("Monsoon Escapes"),
    LOCAL_CUISINE("Local Cuisine"),
    HANDICRAFTS("Handicrafts"),
    ADVENTURE("Adventure & Outdoors")
}

data class CollectionItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val targetCategory: WhereToGoCategory
)

class WhereToGoViewModel(
    private val repository: TruRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow(WhereToGoCategory.ALL)
    val selectedCategory: StateFlow<WhereToGoCategory> = _selectedCategory

    private val _selectedCollection = MutableStateFlow<String?>(null)
    val selectedCollection: StateFlow<String?> = _selectedCollection

    val allDestinations: List<Destination> = repository.getDestinations()

    val collections: List<CollectionItem> = listOf(
        CollectionItem(
            id = "col_hill",
            title = "Hill Stations",
            subtitle = "Mist-clad peaks & panoramic sunsets",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/41/Sunset_in_netarhatt%2C_jharkhand.jpg/1280px-Sunset_in_netarhatt%2C_jharkhand.jpg",
            targetCategory = WhereToGoCategory.HILL_STATIONS
        ),
        CollectionItem(
            id = "col_art",
            title = "Art & Culture",
            subtitle = "Sohrai murals, Chhau & temples",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/70/Sohrai_and_Kohbar_Paintings_01.jpg/960px-Sohrai_and_Kohbar_Paintings_01.jpg",
            targetCategory = WhereToGoCategory.ART_CULTURE
        ),
        CollectionItem(
            id = "col_wild",
            title = "Wildlife",
            subtitle = "Tigers, wild elephants & sal forest",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e1/Monkey_in_betla_park.jpg/1280px-Monkey_in_betla_park.jpg",
            targetCategory = WhereToGoCategory.WILDLIFE
        ),
        CollectionItem(
            id = "col_monsoon",
            title = "Monsoon",
            subtitle = "Cascades in full roaring majesty",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Patratu_dam.jpg/1280px-Patratu_dam.jpg",
            targetCategory = WhereToGoCategory.MONSOON
        ),
        CollectionItem(
            id = "col_falls",
            title = "Waterfalls",
            subtitle = "Glistening plunge pools & river gorges",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Dassam_falls.jpg/1280px-Dassam_falls.jpg",
            targetCategory = WhereToGoCategory.WATERFALLS
        ),
        CollectionItem(
            id = "col_ayurveda",
            title = "Ayurveda & Wellness",
            subtitle = "Tranquil pine groves & healing springs",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Panchghagh_falls.jpg/960px-Panchghagh_falls.jpg",
            targetCategory = WhereToGoCategory.PARKS
        ),
        CollectionItem(
            id = "col_sacred",
            title = "Sacred Journeys",
            subtitle = "Historic Jyotirlingas & Jain summits",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg/1280px-Baidyanath_temple_and_temple_complex%2C_Deoghar_01.jpg",
            targetCategory = WhereToGoCategory.SACRED
        ),
        CollectionItem(
            id = "col_food",
            title = "Local Food",
            subtitle = "Authentic Dhuska, Rugra & Deoghar Peda",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Blog_image.jpg/1280px-Blog_image.jpg",
            targetCategory = WhereToGoCategory.LOCAL_CUISINE
        ),
        CollectionItem(
            id = "col_tribal",
            title = "Tribal Heritage",
            subtitle = "Dokra brass craft & sacred groves",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Dhokra_%28Man%29.jpg/1280px-Dhokra_%28Man%29.jpg",
            targetCategory = WhereToGoCategory.HANDICRAFTS
        ),
        CollectionItem(
            id = "col_weekend",
            title = "Weekend Escapes",
            subtitle = "Serene lake breezes & hillside picnics",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/28/DimnaLake1.jpg/1280px-DimnaLake1.jpg",
            targetCategory = WhereToGoCategory.ADVENTURE
        )
    )

    val filteredDestinations: StateFlow<List<Destination>> = combine(
        _searchQuery,
        _selectedCategory,
        _selectedCollection
    ) { query, category, collection ->
        var list = allDestinations

        // Category filter
        if (category != WhereToGoCategory.ALL) {
            list = list.filter { matchesCategory(it, category) }
        }

        // Search query filter
        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            list = list.filter { dest ->
                dest.name.lowercase().contains(q) ||
                dest.city.lowercase().contains(q) ||
                dest.type.lowercase().contains(q) ||
                dest.description.lowercase().contains(q)
            }
        }

        list
    }.stateIn(viewModelScope, SharingStarted.Lazily, allDestinations)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelected(category: WhereToGoCategory) {
        _selectedCategory.value = category
        _selectedCollection.value = null
    }

    fun onCollectionSelected(collection: CollectionItem) {
        _selectedCategory.value = collection.targetCategory
        _selectedCollection.value = collection.id
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _selectedCategory.value = WhereToGoCategory.ALL
        _selectedCollection.value = null
    }

    fun resetFilters() {
        _selectedCategory.value = WhereToGoCategory.ALL
        _selectedCollection.value = null
    }

    private fun matchesCategory(destination: Destination, category: WhereToGoCategory): Boolean {
        val t = destination.type.lowercase()
        val n = destination.name.lowercase()
        val d = destination.description.lowercase()

        return when (category) {
            WhereToGoCategory.ALL -> true
            WhereToGoCategory.WATERFALLS -> t.contains("waterfall") || n.contains("falls")
            WhereToGoCategory.SACRED -> t.contains("temple") || t.contains("pilgrimage") || n.contains("mandir") || n.contains("dham") || n.contains("shikharji")
            WhereToGoCategory.HILL_STATIONS -> t.contains("hill") || t.contains("peak") || n.contains("netarhat") || n.contains("tagore") || n.contains("parasnath")
            WhereToGoCategory.WILDLIFE -> t.contains("wildlife") || t.contains("national park") || t.contains("sanctuary") || d.contains("tiger") || d.contains("elephant")
            WhereToGoCategory.PARKS -> t.contains("park") || t.contains("garden") || t.contains("lake") || n.contains("garden") || n.contains("lake")
            WhereToGoCategory.ART_CULTURE -> t.contains("heritage") || t.contains("architecture") || t.contains("historical") || n.contains("tagore") || n.contains("sun temple") || n.contains("jagannath")
            WhereToGoCategory.MONSOON -> t.contains("waterfall") || t.contains("valley") || n.contains("patratu") || n.contains("netarhat") || n.contains("hundru") || n.contains("dassam")
            WhereToGoCategory.LOCAL_CUISINE -> destination.city.contains("Ranchi") || destination.city.contains("Deoghar")
            WhereToGoCategory.HANDICRAFTS -> n.contains("panch gagh") || n.contains("sun temple") || n.contains("tagore") || n.contains("jagannath")
            WhereToGoCategory.ADVENTURE -> t.contains("valley") || t.contains("national park") || t.contains("peak") || t.contains("waterfall") || n.contains("patratu") || n.contains("dimna")
        }
    }
}
