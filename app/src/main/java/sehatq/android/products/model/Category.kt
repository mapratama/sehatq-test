package sehatq.android.products.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Category(
    @PrimaryKey()
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("imageUrl") var imageUrl: String
)