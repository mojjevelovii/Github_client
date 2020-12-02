package ru.shumilova.githubclient.mvp.model.repository

import androidx.room.RoomDatabase
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubRepositoriesCache
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubUsersCache
import ru.shumilova.githubclient.mvp.model.repository.dao.IRepositoryDao
import ru.shumilova.githubclient.mvp.model.repository.dao.IUserDao

@androidx.room.Database(
    entities = [RoomGithubUsersCache::class,
        RoomGithubRepositoriesCache::class], version = 1
)
abstract class Database : RoomDatabase() {
    abstract val userDao: IUserDao
    abstract val repositoryDao: IRepositoryDao

    companion object {
        const val DB_NAME = "database.db"
    }
}