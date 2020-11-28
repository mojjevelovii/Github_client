package ru.shumilova.githubclient.mvp.view

interface IUserItemView : IItemView {
    fun setLogin(text: String?)
    fun loadAvatar(url: String)
}