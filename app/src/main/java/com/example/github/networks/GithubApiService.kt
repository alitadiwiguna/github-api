package com.example.github.networks

import com.example.github.models.RepositoryEntity
import com.example.github.models.ResponseData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://api.github.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GithubApiService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") filter: String?, @Query("sort") sort: String?): ResponseData<Array<RepositoryEntity>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repo}")
    suspend fun getDetail(@Path("owner") owner: String, @Path("repo") repo: String): RepositoryEntity
}

object GithubApi {
    val retrofitService : GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java) }
}