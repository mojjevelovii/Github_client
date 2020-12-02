package ru.shumilova.githubclient.mvp.model.repository.dao

import androidx.room.*
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubUsersCache

@Dao
interface IUserDao: IBaseDao<RoomGithubUsersCache> {

    @Query("SELECT * FROM user")
    fun getAll(): List<RoomGithubUsersCache>

    @Query("SELECT * FROM user WHERE user.login = :login LIMIT 1")
    fun findByLogin(login: String): RoomGithubUsersCache?
}