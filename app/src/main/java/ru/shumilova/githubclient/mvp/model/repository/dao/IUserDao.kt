package ru.shumilova.githubclient.mvp.model.repository.dao

import androidx.room.*
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubUserEntity

@Dao
interface IUserDao: IBaseDao<RoomGithubUserEntity> {

    @Query("SELECT * FROM user")
    fun getAll(): List<RoomGithubUserEntity>

    @Query("SELECT * FROM user WHERE user.login = :login LIMIT 1")
    fun findByLogin(login: String): RoomGithubUserEntity?
}