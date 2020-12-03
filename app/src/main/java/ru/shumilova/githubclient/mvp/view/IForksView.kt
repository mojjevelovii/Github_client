package ru.shumilova.githubclient.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.shumilova.githubclient.mvp.model.entity.ForksResponse
import ru.shumilova.githubclient.mvp.model.entity.UserRepo

@AddToEndSingle
interface IForksView : MvpView{
    fun setRepos(repList: ForksResponse)
}