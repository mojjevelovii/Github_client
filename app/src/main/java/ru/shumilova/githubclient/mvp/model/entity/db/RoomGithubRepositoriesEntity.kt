package ru.shumilova.githubclient.mvp.model.entity.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "repo",
    foreignKeys = [ForeignKey(
        entity = RoomGithubUserEntity::class,
        parentColumns = ["uid"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomGithubRepositoriesEntity(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var forksCount: Int = 0,
    var userId: String = ""
)