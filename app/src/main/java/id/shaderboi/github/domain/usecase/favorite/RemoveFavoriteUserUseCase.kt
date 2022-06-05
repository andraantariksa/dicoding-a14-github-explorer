package id.shaderboi.github.domain.usecase.favorite

import id.shaderboi.github.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteUserUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(id: Int) {
        favoriteRepository.remove(id)
    }
}