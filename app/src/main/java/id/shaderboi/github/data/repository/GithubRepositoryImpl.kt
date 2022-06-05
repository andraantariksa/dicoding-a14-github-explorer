package id.shaderboi.github.data.repository

import id.shaderboi.github.data.data_source.network.GithubService
import id.shaderboi.github.domain.model.SearchResult
import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubService: GithubService): GithubRepository {
    override suspend fun getUsers(): List<UserBrief> = githubService.getUsers()
    override suspend fun getUser(username: String): User = githubService.getUser(username)
    override suspend fun searchUser(query: String): SearchResult = githubService.searchUser(query)
    override suspend fun getUserFollowing(username: String): List<UserBrief> = githubService.getFollowing(username)
    override suspend fun getUserFollowers(username: String): List<UserBrief> = githubService.getFollowers(username)
}