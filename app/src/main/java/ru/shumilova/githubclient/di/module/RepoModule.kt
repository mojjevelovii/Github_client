package ru.shumilova.githubclient.di.module

import dagger.Module
import dagger.Provides
import ru.shumilova.githubclient.mvp.model.api.IDataSource
import ru.shumilova.githubclient.mvp.model.repository.Database
import ru.shumilova.githubclient.mvp.model.repository.GithubUsersRepo
import ru.shumilova.githubclient.mvp.model.repository.IGithubUsersRepo
import ru.shumilova.githubclient.mvp.model.utils.INetworkStatus
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun userRepo(api: IDataSource, networkStatus: INetworkStatus, db:Database):IGithubUsersRepo =
        GithubUsersRepo(api, networkStatus, db)
}