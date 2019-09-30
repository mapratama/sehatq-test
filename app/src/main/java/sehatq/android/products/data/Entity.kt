package sehatq.android.products.data

import sehatq.android.products.model.Category
import sehatq.android.products.model.Product

data class HomeResponse(val data: ProductResponse)

data class ProductResponse(val productPromo: List<Product>?, val category: List<Category>)