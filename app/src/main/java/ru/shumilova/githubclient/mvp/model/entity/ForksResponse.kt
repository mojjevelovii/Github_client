package ru.shumilova.githubclient.mvp.model.entity

data class ForksResponse(
    val forksCount: Int,
    val forks: List<UserRepo> = emptyList()
)