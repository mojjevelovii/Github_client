package ru.shumilova.githubclient.mvp.presenter

import moxy.MvpPresenter
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.view.IUserView
import ru.terrakok.cicerone.Router

class UserPresenter (val router: Router, val user:GithubUser): MvpPresenter<IUserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        user.login?.let { viewState.setLogin(it)}
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }


}