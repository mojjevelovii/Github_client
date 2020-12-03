package ru.shumilova.githubclient.mvp.model.entity.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class RoomGithubUserEntity(
    @PrimaryKey
    val uid: String = "",
    var login: String = "",
    var avatarUrl: String = "",
    var reposUrl: String = "",
    val forksCount: Int = 0
)