package id.co.evolution.mygithubuserapps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.co.evolution.mygithubuserapps.feature.users.data.local.UserDao
import id.co.evolution.mygithubuserapps.feature.users.data.local.UserEntity


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "github_user_db"
    }
}