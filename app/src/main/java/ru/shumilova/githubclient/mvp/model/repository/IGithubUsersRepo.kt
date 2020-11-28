package ru.shumilova.githubclient.mvp.model.repository

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>

    fun getRepos(repoUrl: String): @NonNull Single<List<UserRepo>>
}