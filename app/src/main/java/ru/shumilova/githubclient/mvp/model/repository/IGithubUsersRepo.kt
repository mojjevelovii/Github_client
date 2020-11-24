package ru.shumilova.githubclient.mvp.model.repository

import io.reactivex.rxjava3.core.Single
import ru.shumilova.githubclient.mvp.model.entity.GithubUser

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>

}