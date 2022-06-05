package id.shaderboi.github.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.github.domain.usecase.favorite.FavoriteUserUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val favoriteUserUseCases: FavoriteUserUseCases): ViewModel() {
    val listFavorite get() = favoriteUserUseCases.getFavoriteUsers()

    fun removeFavorite(id: Int) = viewModelScope.launch {
        favoriteUserUseCases.removeFavoriteUser(id)
    }
}