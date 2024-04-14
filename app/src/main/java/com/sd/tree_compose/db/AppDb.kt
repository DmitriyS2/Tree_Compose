package com.sd.tree_compose.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sd.tree_compose.dao.NodeDao
import com.sd.tree_compose.entity.NodeItemEntity

@Database(
    entities = [NodeItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}