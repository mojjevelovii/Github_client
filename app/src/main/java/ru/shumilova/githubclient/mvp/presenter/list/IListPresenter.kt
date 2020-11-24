package ru.shumilova.githubclient.mvp.presenter.list

import ru.shumilova.githubclient.mvp.view.IItemView

interface IListPresenter<V : IItemView?> {
    fun bindView(view: V)
    fun onItemClick(view: V)
    val count: Int
}