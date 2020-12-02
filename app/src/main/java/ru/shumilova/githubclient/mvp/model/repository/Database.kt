package ru.shumilova.githubclient.mvp.model.repository

import android.content.Context
import androidx.room.Room
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
        private const val DB_NAME = "database.db"
        private var instance: Database? = null
        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created. Please call create(context)")

        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, Database::class.java, DB_NAME)
                    .build()
            }
        }
    }
}