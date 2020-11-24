package ru.shumilova.githubclient.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.shumilova.githubclient.mvp.presenter.UserPresenter

@AddToEndSingle
interface IUserView : MvpView {
    fun setLogin(s: String)
}