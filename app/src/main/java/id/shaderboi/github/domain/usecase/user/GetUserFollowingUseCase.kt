package id.shaderboi.github.domain.usecase.user

import id.shaderboi.github.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserFollowingUseCase @Inject constructor(private val repository: GithubRepository) {
    suspend operator fun invoke(username: String) = repository.getUserFollowing(username)
}