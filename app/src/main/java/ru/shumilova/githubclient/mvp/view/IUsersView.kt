package ru.shumilova.githubclient.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface IUsersView : MvpView {
    fun init()
    fun updateList()
}