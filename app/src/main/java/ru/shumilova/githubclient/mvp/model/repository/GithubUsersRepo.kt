package ru.shumilova.githubclient.mvp.model.repository

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.shumilova.githubclient.mvp.model.api.IDataSource
import ru.shumilova.githubclient.mvp.model.entity.ForksResponse
import ru.shumilova.githubclient.mvp.model.entity.GithubUser
import ru.shumilova.githubclient.mvp.model.entity.UserRepo
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubRepositoriesEntity
import ru.shumilova.githubclient.mvp.model.entity.db.RoomGithubUserEntity
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
                        RoomGithubUserEntity(
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
                        .map { repos ->
                            val cachedUser = db.userDao.findByLogin(user.login!!)
                                ?: throw RuntimeException("No such user in cache")
                            val reposCache = repos.map {
                                db.repositoryDao.findByName(it.name!!)
                                    ?: RoomGithubRepositoriesEntity(
                                        id = it.id ?: "",
                                        name = it.name ?: "",
                                        forksCount = 0,
                                        userId = cachedUser.uid
                                    )
                            }
                            db.repositoryDao.insert(reposCache)
                            repos
                        }
                } ?: throw RuntimeException("User has no repos url")

            } else {
                Single.fromCallable {
                    val roomUser = user.login?.let {
                        db.userDao.findByLogin(it)
                    } ?: throw RuntimeException("No such user in cache")

                    db.repositoryDao.findForUser(roomUser.uid).map {
                        UserRepo(id = it.id, name = it.name, forksCount = it.forksCount)
                    }
                }
            }
        }.subscribeOn(Schedulers.io())

    override fun getForks(repo: UserRepo): Single<ForksResponse> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                repo.forksUrl?.let { url ->
                    api.getRepos(url)
                        .map { repos ->
                            val cachedRepo = db.repositoryDao.findByName(repo.name!!)
                                ?: throw RuntimeException("No such user in cache")
                            val countedForksRepo = cachedRepo.copy(forksCount = repos.size)
                            db.repositoryDao.insert(countedForksRepo)
                            ForksResponse(repos.size, repos)
                        }
                } ?: throw RuntimeException("User has no repos url")

            } else {
                Single.fromCallable {
                    val restoredRepo = db.repositoryDao.findByName(repo.name!!)
                        ?: throw RuntimeException("No such user in cache")
                    ForksResponse(restoredRepo.forksCount)
                }
            }
        }.subscribeOn(Schedulers.io())


}