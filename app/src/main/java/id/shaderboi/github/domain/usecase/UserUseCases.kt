package id.shaderboi.github.domain.usecase

data class UserUseCases(
    val getUsers: GetUsersUseCase,
    val getUser: GetUserUseCase,
    val searchUser: SearchUserUseCase,
    val getUserFollowers: GetUserFollowersUseCase,
    val getUserFollowing: GetUserFollowingUseCase,
)