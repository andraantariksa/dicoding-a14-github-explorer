package id.shaderboi.github.ui.user

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.usecase.GetUserUseCase
import id.shaderboi.github.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase): ViewModel() {
    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val user = _user.asStateFlow()

    lateinit var userBrief: UserBrief

    suspend fun getUser(username: String) {
        _user.value = Resource.Loading()
        try {
            _user.value = Resource.Loaded(getUserUseCase(username))
        } catch (ex: Exception) {
            _user.value = Resource.Error(ex)
        }
    }
}