package com.example.github.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github.networks.GithubApi
import kotlinx.coroutines.launch


class GithubViewModel: ViewModel() {

    private val list = MutableLiveData<List<RepositoryEntity>>()
    val data: LiveData<List<RepositoryEntity>> = list

    init {
        getGithubRepository(null, null)
    }

    fun getGithubRepository(filter: String?, sortBy: String?) {
        if (filter === null) {
            return
        }

        viewModelScope.launch {
            try {
                val listResult = GithubApi.retrofitService.getRepositories(filter, sortBy)
                list.value = listResult.items.toList()
            } catch (e: Exception) {
                Log.d("error fetch data", e.message.toString())
                list.value = emptyList()
            }
        }
    }
}