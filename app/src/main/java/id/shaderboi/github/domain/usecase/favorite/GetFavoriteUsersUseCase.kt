package id.shaderboi.github.domain.usecase.favorite

import id.shaderboi.github.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteUsersUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke() = favoriteRepository.data
}