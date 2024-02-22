package com.example.testtask.data.user_model

data class Location(
    val city: String?,
    val coordinates: Coordinates?,
    val country: String?,
    val postcode: Any?,
    val state: String?,
    val street: Street?,
    val timezone: Timezone?
)