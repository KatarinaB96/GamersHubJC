package com.example.gamershub.domain.models

data class Game(
        val id: Int,
        val slug: String,
        val name: String,
        val description: String,
        val released: String,
        val backgroundImage: String,
        val website: String,
        val rating: Double
    )