package id.shaderboi.github.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.usecase.favorite.FavoriteUserUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteUserUseCases: FavoriteUserUseCases) :
    ViewModel() {
    private val _listFavorite = MutableStateFlow<List<UserBrief>>(listOf())
    val listFavorite = _listFavorite.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteUserUseCases
                .getFavoriteUsers()
                .collectLatest { listFavorite ->
                    _listFavorite.value = listFavorite
                }
        }
    }

    fun removeFavorite(id: Int) = viewModelScope.launch {
        favoriteUserUseCases.removeFavoriteUser(id)
    }
}