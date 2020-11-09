package ru.shumilova.githubclient.mvp.presenter

import moxy.MvpPresenter
import ru.shumilova.githubclient.GithubApplication
import ru.shumilova.githubclient.mvp.view.IMainView
import ru.shumilova.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router


class MainPresenter : MvpPresenter<IMainView>() {
    private val router: Router? = GithubApplication.application?.router
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        // TODO: Nothing to do
        router?.replaceScreen(Screens.UsersScreen())
    }

    fun backClicked() {
        router?.exit()
    }
}