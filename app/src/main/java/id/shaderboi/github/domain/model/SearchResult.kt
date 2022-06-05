package id.shaderboi.github.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResult(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    @Json(name = "items")
    val userSearchBriefs: List<UserBrief>,
    @Json(name = "total_count")
    val totalCount: Int
)