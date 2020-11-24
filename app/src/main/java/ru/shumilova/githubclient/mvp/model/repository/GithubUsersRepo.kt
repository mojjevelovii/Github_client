package ru.shumilova.githubclient.mvp.model.repository

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.shumilova.githubclient.mvp.model.api.IDataSource

class GithubUsersRepo(val api: IDataSource) : IGithubUsersRepo {
    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
}