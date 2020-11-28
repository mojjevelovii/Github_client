package ru.shumilova.githubclient.mvp.view.image

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}