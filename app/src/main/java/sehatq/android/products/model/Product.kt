package sehatq.android.products.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @PrimaryKey()
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("price") var price: String,
    @SerializedName("imageUrl") var imageUrl: String,
    @SerializedName("loved") var isLoved: Int
)