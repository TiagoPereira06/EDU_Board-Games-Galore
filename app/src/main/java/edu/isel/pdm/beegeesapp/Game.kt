package edu.isel.pdm.beegeesapp

data class Game(
    val id: Int,
    val minPlayTime: Int,
    val maxPlayTime: Int,
    val minPlayers: Int,
    val maxPlayers: Int,
    val minAge: Int,
    val numberUserReviews: Int,
    val yearPublished: Int,
    val name: String,
    val description: String,
    val publisher: String,
    val developers: String,
    val rulesURL: String,
    val gameURL: String,
    val price: Double,
    val averageUserRating: Double
)