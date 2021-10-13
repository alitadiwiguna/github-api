package com.example.github.models

import com.squareup.moshi.Json

data class RepositoryEntity (
    val id: Int,
    @Json(name = "node_id") val nodeId: String?,
    val name: String,
    @Json(name = "full_name") val fullName: String?,
    val private: Boolean,
    @Json(name = "html_url") val htmlUrl: String?,
    val description: String?,
    val owner: OwnerModel?,
    val watchers: Int?,
    val forks: Int?,
    @Json(name = "open_issues") val issues: Int?,
    @Json(name = "updated_at") val updatedAt: String?,
    @Json(name = "created_at") val createdAt: String?,
    val language: String?

)

data class OwnerModel (
    val login: String,
    val id: Int,
    @Json(name = "node_id") val nodeId: String,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "gravatar_id") val gravatarId: String,
    val url: String,
    @Json(name = "html_url") val htmlUrl: String,
    @Json(name = "followers_url") val followersUrl: String,
    @Json(name = "following_url") val followingUrl: String,
    @Json(name = "gists_url") val gistsUrl: String,
    @Json(name = "starred_url") val starredUrl: String,
    @Json(name = "subscriptions_url") val subscriptionsUrl: String,
    @Json(name = "organizations_url") val organizationsUrl: String,
    @Json(name = "repos_url") val reposUrl: String,
    @Json(name = "events_url") val eventsUrl: String,
    @Json(name = "received_events_url") val receivedEventsUrl: String,
    val type: String,
    @Json(name = "site_admin") val siteAdmin: Boolean
)

data class ResponseData<T> (
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    val items: T
    )
