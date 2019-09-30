package sehatq.android.products.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sehatq.android.products.core.App
import sehatq.android.products.model.Category
import sehatq.android.products.model.Product
import sehatq.android.products.model.Purchase
import simple.mvp.foodlist.data.db.DatabaseDao

@Database(entities = [Product::class, Category::class, Purchase::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): DatabaseDao

    companion object {
        const val DB_NAME = "sehatq.db"
        const val VERSION = 1
        private val instance: AppDatabase by lazy { create(App.instance) }

        @Synchronized
        internal fun getInstance(): AppDatabase {
            return instance
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
        }

    }
}

