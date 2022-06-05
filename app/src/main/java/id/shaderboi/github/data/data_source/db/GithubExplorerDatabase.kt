package id.shaderboi.github.data.data_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import id.shaderboi.github.data.data_source.db.dao.FavoriteDao
import id.shaderboi.github.data.data_source.db.entity.UserBriefFavoriteEntity

@Database(entities = [UserBriefFavoriteEntity::class], version = 2)
abstract class GithubExplorerDatabase: RoomDatabase() {
    abstract val favoriteDao: FavoriteDao

    companion object {
        const val NAME = "GithubExplorer"
    }
}