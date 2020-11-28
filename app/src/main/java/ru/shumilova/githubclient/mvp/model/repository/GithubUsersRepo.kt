package ru.shumilova.githubclient.mvp.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.shumilova.githubclient.mvp.model.api.IDataSource
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo

class GithubUsersRepo(private val api: IDataSource) : IGithubUsersRepo {
    override fun getUsers(): Single<List<GithubUser>> = api.getUsers().subscribeOn(Schedulers.io())

    override fun getRepos(repoUrl: String): @NonNull Single<List<UserRepo>> =
        api.getRepos(repoUrl).subscribeOn(Schedulers.io())

}