package simple.mvp.foodlist.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sehatq.android.products.model.Category
import sehatq.android.products.model.Product
import sehatq.android.products.model.Purchase

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPurchase(purchase: Purchase): Long

    @Query("SELECT * from Product")
    fun selectAllProduct(): MutableList<Product>

    @Query("SELECT * from Category")
    fun selectAllCategory(): MutableList<Category>

    @Query("SELECT * from Purchase ORDER BY id")
    fun selectAllPurchase(): MutableList<Purchase>

    @Query("SELECT * from Product where id=:id limit 1")
    fun getProduct(id: Int): Product

    @Query("UPDATE Product Set isLoved=:isLoved where id=:id")
    fun setFavoriteProduct(id: Int, isLoved: Int)
}