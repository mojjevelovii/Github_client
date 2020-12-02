package ru.shumilova.githubclient.di.module

import dagger.Module
import dagger.Provides
import ru.shumilova.githubclient.GithubApplication

@Module
class AppModule(private val app: GithubApplication) {

    @Provides
    fun app(): GithubApplication = app
}