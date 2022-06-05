package id.shaderboi.github.domain.usecase

import id.shaderboi.github.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserFollowersUseCase @Inject constructor(private val repository: GithubRepository) {
    suspend operator fun invoke(username: String) = repository.getUserFollowers(username)
}