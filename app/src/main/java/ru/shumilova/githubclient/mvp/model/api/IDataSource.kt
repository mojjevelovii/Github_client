package ru.shumilova.githubclient.mvp.model.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import ru.shumilova.githubclient.mvp.model.entity.GithubUser

interface IDataSource {
    @GET("/users")
    fun getUsers(): Single<List<GithubUser>>
}
