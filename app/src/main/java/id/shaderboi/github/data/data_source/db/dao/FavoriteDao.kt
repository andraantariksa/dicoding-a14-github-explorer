package id.shaderboi.github.data.data_source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.shaderboi.github.data.data_source.db.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun removeFavorite(id: Int)
}