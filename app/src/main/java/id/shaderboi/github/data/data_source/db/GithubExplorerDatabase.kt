package id.shaderboi.github.data.data_source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.shaderboi.github.data.data_source.db.converters.UserBriefConverter
import id.shaderboi.github.data.data_source.db.dao.FavoriteDao
import id.shaderboi.github.data.data_source.db.entity.FavoriteEntity

@TypeConverters(UserBriefConverter::class)
@Database(entities = [FavoriteEntity::class], version = 0)
abstract class GithubExplorerDatabase: RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
}