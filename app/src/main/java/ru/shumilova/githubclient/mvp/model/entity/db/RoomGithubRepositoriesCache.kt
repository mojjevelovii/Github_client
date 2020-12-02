package ru.shumilova.githubclient.mvp.model.entity.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "repo",
    foreignKeys = [ForeignKey(
        entity = RoomGithubUsersCache::class,
        parentColumns = ["uid"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
class RoomGithubRepositoriesCache(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var forksCount: Int = 0,
    var userId: String = ""
)