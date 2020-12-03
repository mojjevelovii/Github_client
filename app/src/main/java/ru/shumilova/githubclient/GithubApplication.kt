package ru.shumilova.githubclient

import android.app.Application
import dagger.Component
import ru.shumilova.githubclient.di.components.DaggerIAppComponent
import ru.shumilova.githubclient.di.components.IAppComponent
import ru.shumilova.githubclient.di.module.AppModule
import ru.shumilova.githubclient.mvp.model.repository.Database
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class GithubApplication : Application() {
    lateinit var appComponent: IAppComponent

    companion object {
        lateinit var application: GithubApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        appComponent = DaggerIAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
