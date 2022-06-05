package id.shaderboi.github.domain.usecase

import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.repository.GithubRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val repository: GithubRepository) {
    suspend operator fun invoke(): List<UserBrief> = repository.getUsers()
}