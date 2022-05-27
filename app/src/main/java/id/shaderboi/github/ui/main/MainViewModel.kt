package id.shaderboi.github.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.use_case.UserUseCases
import id.shaderboi.github.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {
    private val _users = MutableStateFlow<Resource<List<UserBrief>>>(Resource.Loading())
    val users = _users.asStateFlow()

    init {
        fetchUsers()
    }

    fun fetchUsers() = viewModelScope.launch {
        _users.value = Resource.Loading()
        try {
            _users.value = Resource.Loaded(userUseCases.getUsers())
        } catch (ex: Exception) {
            _users.value = Resource.Error(ex)
        }
    }
}