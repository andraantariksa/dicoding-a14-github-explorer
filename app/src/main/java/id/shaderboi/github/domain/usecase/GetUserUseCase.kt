package id.shaderboi.github.domain.usecase

import id.shaderboi.github.domain.model.User
import id.shaderboi.github.domain.repository.GithubRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: GithubRepository) {
    suspend operator fun invoke(username: String): User = repository.getUser(username)
}