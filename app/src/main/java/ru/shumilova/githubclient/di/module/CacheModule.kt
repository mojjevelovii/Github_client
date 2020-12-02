package ru.shumilova.githubclient.di.module

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.shumilova.githubclient.GithubApplication
import ru.shumilova.githubclient.mvp.model.repository.Database
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: GithubApplication): Database =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()
}