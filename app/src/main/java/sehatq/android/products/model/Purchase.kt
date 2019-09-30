package sehatq.android.products.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(tableName = "Purchase",
        foreignKeys = [
                ForeignKey(entity = Product::class,
                        parentColumns = ["id"],
                        childColumns = ["product_id"],
                        onDelete = CASCADE)])

data class Purchase(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        var product_id: Int
)