package id.shaderboi.github.data.data_source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.shaderboi.github.data.data_source.db.entity.UserBriefFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun add(favorite: UserBriefFavoriteEntity)

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun remove(id: Int)

    @Query("SELECT * FROM favorite")
    fun get(): Flow<List<UserBriefFavoriteEntity>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun get(id: Int): Flow<UserBriefFavoriteEntity?>
}