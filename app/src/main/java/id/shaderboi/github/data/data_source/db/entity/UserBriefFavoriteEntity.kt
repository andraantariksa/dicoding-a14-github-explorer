package id.shaderboi.github.data.data_source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import id.shaderboi.github.domain.model.UserBrief

@Entity(tableName = "favorite")
data class UserBriefFavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val avatarUrl: String?,
    override val eventsUrl: String?,
    override val followersUrl: String,
    override val followingUrl: String,
    override val gistsUrl: String,
    override val gravatarId: String?,
    override val htmlUrl: String,
    override val login: String,
    override val nodeId: String,
    override val organizationsUrl: String,
    override val receivedEventsUrl: String,
    override val reposUrl: String,
    override val siteAdmin: Boolean,
    override val starredUrl: String,
    override val subscriptionsUrl: String,
    override val type: String,
    override val url: String
) : UserBrief(
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