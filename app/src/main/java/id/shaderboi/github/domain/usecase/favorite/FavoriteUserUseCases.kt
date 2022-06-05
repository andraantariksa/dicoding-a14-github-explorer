package id.shaderboi.github.domain.usecase.favorite

data class FavoriteUserUseCases(
    val getFavoriteUsers: GetFavoriteUsersUseCase,
    val getFavoriteUser: GetFavoriteUserUseCase,
    val removeFavoriteUser: RemoveFavoriteUserUseCase,
    val addFavoriteUser: AddFavoriteUserUseCase,
)