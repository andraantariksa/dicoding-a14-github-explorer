package id.shaderboi.github.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.usecase.UserUseCases
import id.shaderboi.github.domain.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {
    private val _users = MutableStateFlow<Resource<List<UserBrief>>>(Resource.Loading())
    val users = _users.asStateFlow()

    init {
        queryUsers()
    }

    private var job: Job? = null

    fun queryUsers(query: String? = null, direct: Boolean = false) {
        job?.cancel()
        job = viewModelScope.launch {
            if (!(query == null || query.isBlank())) {
                delay(3000)
            }
            _users.value = Resource.Loading()
            if (query == null || query.isBlank()) {
                try {
                    _users.value = Resource.Loaded(userUseCases.getUsers())
                } catch (ex: Exception) {
                    _users.value = Resource.Error(ex)
                }
                return@launch
            }

            try {
                _users.value = Resource.Loaded(userUseCases.searchUser(query).userSearchBriefs)
            } catch (ex: Exception) {
                _users.value = Resource.Error(ex)
            }
        }
    }
}