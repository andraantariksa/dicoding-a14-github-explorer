package id.shaderboi.github.data.data_source.network

import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.model.UserBrief
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users")
    suspend fun getUsers(): List<UserBrief>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): User
}