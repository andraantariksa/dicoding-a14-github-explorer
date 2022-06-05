package id.shaderboi.github.domain.usecase.user

import id.shaderboi.github.domain.repository.GithubRepository
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(private val repository: GithubRepository) {
    suspend operator fun invoke(query: String) = repository.searchUser(query)
}