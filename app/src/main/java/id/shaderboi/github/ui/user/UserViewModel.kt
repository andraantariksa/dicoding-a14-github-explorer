package id.shaderboi.github.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.usecase.favorite.FavoriteUserUseCases
import id.shaderboi.github.domain.usecase.user.UserUseCases
import id.shaderboi.github.domain.util.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val favoriteUserUseCases: FavoriteUserUseCases
) : ViewModel() {
    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading())
    val user = _user.asStateFlow()

    lateinit var userBrief: UserBrief

    fun isUserFavorited(id: Int): Flow<Boolean> =
        favoriteUserUseCases.getFavoriteUser(id).map { userBrief -> userBrief != null }

    suspend fun getUser(username: String) {
        _user.value = Resource.Loading()
        try {
            _user.value = Resource.Loaded(userUseCases.getUser(username))
        } catch (ex: Exception) {
            _user.value = Resource.Error(ex)
        }
    }

    fun toggleUserFavorite(userBrief: UserBrief) = viewModelScope.launch {
        val isFavorited = favoriteUserUseCases.getFavoriteUser(userBrief.id).first() != null
        if (isFavorited) {
            favoriteUserUseCases.removeFavoriteUser(userBrief.id)
        } else {
            favoriteUserUseCases.addFavoriteUser(userBrief)
        }
    }
}