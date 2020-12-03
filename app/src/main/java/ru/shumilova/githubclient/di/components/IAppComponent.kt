package ru.shumilova.githubclient.di.components

import dagger.Component
import ru.shumilova.githubclient.MainActivity
import ru.shumilova.githubclient.di.module.*
import ru.shumilova.githubclient.mvp.presenter.ForkPresenter
import ru.shumilova.githubclient.mvp.presenter.MainPresenter
import ru.shumilova.githubclient.mvp.presenter.UserPresenter
import ru.shumilova.githubclient.mvp.presenter.UsersPresenter
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        CacheModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)

interface IAppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(forkPresenter: ForkPresenter)
}