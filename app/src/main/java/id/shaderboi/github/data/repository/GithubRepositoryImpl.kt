package id.shaderboi.github.data.repository

import id.shaderboi.github.data.data_source.network.GithubService
import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubService: GithubService): GithubRepository {
    override suspend fun getUsers(): List<UserBrief> = githubService.getUsers()

    override suspend fun getUser(username: String): User = githubService.getUser(username)
}