package ru.shumilova.githubclient.mvp.presenter

import moxy.MvpPresenter
import ru.shumilova.githubclient.GithubApplication
import ru.shumilova.githubclient.mvp.view.IUsersView
import ru.shumilova.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router

class UserPresenter : MvpPresenter<IUsersView?>() {
    private val router: Router? = GithubApplication.application?.router

    fun backPressed(): Boolean {
        router?.backTo(Screens.UsersScreen())
        return false
    }
}