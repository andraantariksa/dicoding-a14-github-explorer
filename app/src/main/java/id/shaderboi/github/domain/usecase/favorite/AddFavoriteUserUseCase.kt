package id.shaderboi.github.domain.usecase.favorite

import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteUserUseCase @Inject constructor(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(userBrief: UserBrief) {
        favoriteRepository.add(userBrief)
    }
}