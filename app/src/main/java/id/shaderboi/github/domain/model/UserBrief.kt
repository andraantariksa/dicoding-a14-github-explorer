package id.shaderboi.github.domain.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import id.shaderboi.github.data.data_source.db.entity.UserBriefFavoriteEntity
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
open class UserBrief(
    @Json(name = "avatar_url")
    open val avatarUrl: String?,
    @Json(name = "events_url")
    open val eventsUrl: String?,
    @Json(name = "followers_url")
    open val followersUrl: String,
    @Json(name = "following_url")
    open val followingUrl: String,
    @Json(name = "gists_url")
    open val gistsUrl: String,
    @Json(name = "gravatar_id")
    open val gravatarId: String?,
    @Json(name = "html_url")
    open val htmlUrl: String,
    @Json(name = "id")
    open val id: Int,
    @Json(name = "login")
    open val login: String,
    @Json(name = "node_id")
    open val nodeId: String,
    @Json(name = "organizations_url")
    open val organizationsUrl: String,
    @Json(name = "received_events_url")
    open val receivedEventsUrl: String,
    @Json(name = "repos_url")
    open val reposUrl: String,
    @Json(name = "site_admin")
    open val siteAdmin: Boolean,
    @Json(name = "starred_url")
    open val starredUrl: String,
    @Json(name = "subscriptions_url")
    open val subscriptionsUrl: String,
    @Json(name = "type")
    open val type: String,
    @Json(name = "url")
    open val url: String
) : Parcelable {
    fun toEntity(): UserBriefFavoriteEntity =
        UserBriefFavoriteEntity(
            id = id,
            avatarUrl = avatarUrl,
            eventsUrl = eventsUrl,
            followersUrl = followersUrl,
            followingUrl = followingUrl,
            gistsUrl = gistsUrl,
            gravatarId = gravatarId,
            htmlUrl = htmlUrl,
            login = login,
            nodeId = nodeId,
            organizationsUrl = organizationsUrl,
            receivedEventsUrl = receivedEventsUrl,
            reposUrl = reposUrl,
            siteAdmin = siteAdmin,
            starredUrl = starredUrl,
            subscriptionsUrl = subscriptionsUrl,
            type = type,
            url = url
        )
}