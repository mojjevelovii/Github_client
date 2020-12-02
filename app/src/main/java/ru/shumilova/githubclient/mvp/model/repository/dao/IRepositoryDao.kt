package ru.shumilova.githubclient.mvp.model.repository.dao

import androidx.room.Dao
import androidx.room.Query
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubRepositoriesCache

@Dao
interface IRepositoryDao : IBaseDao<RoomGithubRepositoriesCache> {
    @Query("SELECT * FROM repo")
    fun getAll(): List<RoomGithubRepositoriesCache>

    @Query("SELECT * FROM repo WHERE repo.userId = :userId")
    fun findForUser(userId: String): List<RoomGithubRepositoriesCache>
}