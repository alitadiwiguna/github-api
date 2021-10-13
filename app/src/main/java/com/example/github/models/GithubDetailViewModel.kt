package com.example.github.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.networks.GithubApi
import kotlinx.coroutines.launch


class GithubDetailViewModel: ViewModel() {

    private val detail = MutableLiveData<RepositoryEntity>()
    val liveDataDetail: LiveData<RepositoryEntity> = detail


    fun getDetail(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                val result = GithubApi.retrofitService.getDetail(owner, repo)
                detail.value = result
            } catch (e: Exception) {
                Log.d("Detail", "Repo not found")
            }
        }
    }
}