package ru.shumilova.githubclient.mvp.model.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.shumilova.githubclient.mvp.model.api.IDataSource
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubRepositoriesCache
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubUsersCache
import ru.shumilova.githubclient.mvp.model.utils.INetworkStatus

class GithubUsersRepo(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val db: Database
) : IGithubUsersRepo {
    override fun getUsers() = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUsers().flatMap { users ->
                Single.fromCallable {
                    val roomUsers = users.map { user ->
                        RoomGithubUsersCache(
                            user.id ?: "",
                            user.login ?: "",
                            user.avatarUrl ?: "",
                            user.reposUrl ?: ""
                        )
                    }
                    db.userDao.insert(roomUsers)
                    users
                }
            }
        } else {
            Single.fromCallable {
                db.userDao.getAll().map { roomUser ->
                    GithubUser(
                        roomUser.uid,
                        roomUser.login,
                        roomUser.avatarUrl,
                        roomUser.reposUrl
                    )
                }
            }
        }
    }.subscribeOn(Schedulers.io())

    override fun getRepos(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getRepos(url)
                        .flatMap {
                            val repos = it.map { repo ->
                                api.getRepos(repo.forksUrl ?: "")
                                    .map {
                                        Pair(repo, it.size)
                                    }
                            }
                            Single.zip(repos) { array -> array as List<Pair<UserRepo, Int>> }
                        }.flatMap { repoPairs ->
                            Single.fromCallable {
                                val roomUser = user.login?.let {
                                    db.userDao.findByLogin(it)
                                } ?: throw RuntimeException("No such user in cache")
                                val roomRepos = repoPairs.map {
                                    RoomGithubRepositoriesCache(
                                        id = it.first.id ?: "",
                                        name = it.first.name ?: "",
                                        forksCount = it.second,
                                        userId = roomUser.uid
                                    )
                                }

                                db.repositoryDao.insert(roomRepos)
                                repoPairs.map { list -> list.first }
                            }
                        }
                } ?: throw RuntimeException("User has no repos url")

                } else {
                    Single.fromCallable {
                        val roomUser = user.login?.let {
                            db.userDao.findByLogin(it)
                        } ?: throw RuntimeException("No such user in cache")

                        db.repositoryDao.findForUser(roomUser.uid).map {
                            UserRepo(it.id, it.name, forksCount = it.forksCount)
                        }
                    }
                }
            }.subscribeOn(Schedulers.io())
}