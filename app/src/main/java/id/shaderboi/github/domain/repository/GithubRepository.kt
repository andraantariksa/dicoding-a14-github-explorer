package id.shaderboi.github.domain.repository

import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.model.UserBrief

interface GithubRepository {
    suspend fun getUsers(): List<UserBrief>
    suspend fun getUser(username: String): User
}