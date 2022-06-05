package id.shaderboi.github.data.data_source.network

import id.shaderboi.github.domain.model.SearchResult
import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.model.UserBrief
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users")
    suspend fun getUsers(): List<UserBrief>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): User

    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String): SearchResult

    @GET("users/{username}/followers")
    suspend fun getFollowers(@Path("username") username: String): List<UserBrief>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<UserBrief>
}