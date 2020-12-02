package ru.shumilova.githubclient.mvp.presenter

import moxy.MvpPresenter
import ru.shumilova.githubclient.mvp.view.IMainView
import ru.shumilova.githubclient.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainPresenter : MvpPresenter<IMainView>() {
    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.replaceScreen(Screens.UsersScreen())
    }

    fun backClicked() {
        router.exit()
    }
}