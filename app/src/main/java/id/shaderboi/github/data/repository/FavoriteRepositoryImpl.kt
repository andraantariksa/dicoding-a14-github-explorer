package id.shaderboi.github.data.repository

import id.shaderboi.github.data.data_source.db.GithubExplorerDatabase
import id.shaderboi.github.domain.model.UserBrief
import id.shaderboi.github.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl(database: GithubExplorerDatabase) : FavoriteRepository {
    private val favoriteDao = database.favoriteDao

    override fun get(): Flow<List<UserBrief>> = favoriteDao.get()

    override suspend fun add(userBrief: UserBrief) {
        favoriteDao.add(userBrief.toEntity())
    }

    override fun get(id: Int): Flow<UserBrief?> = favoriteDao.get(id)

    override suspend fun remove(id: Int) {
        favoriteDao.remove(id)
    }
}