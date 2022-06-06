package id.shaderboi.github.domain.repository

import id.shaderboi.github.domain.model.UserBrief
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun add(userBrief: UserBrief)
    fun get(id: Int): Flow<UserBrief?>
    suspend fun remove(id: Int)
    fun get(): Flow<List<UserBrief>>
}