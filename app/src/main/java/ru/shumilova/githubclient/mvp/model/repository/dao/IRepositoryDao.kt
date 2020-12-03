package ru.shumilova.githubclient.mvp.model.repository.dao

import androidx.room.Dao
import androidx.room.Query
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubRepositoriesEntity

@Dao
interface IRepositoryDao : IBaseDao<RoomGithubRepositoriesEntity> {
    @Query("SELECT * FROM repo")
    fun getAll(): List<RoomGithubRepositoriesEntity>

    @Query("SELECT * FROM repo WHERE repo.userId = :userId")
    fun findForUser(userId: String): List<RoomGithubRepositoriesEntity>

    @Query("SELECT * FROM repo WHERE repo.name = :repoName LIMIT 1")
    fun findByName(repoName: String): RoomGithubRepositoriesEntity?
}