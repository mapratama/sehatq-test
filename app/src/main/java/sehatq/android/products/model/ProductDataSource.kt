package sehatq.android.products.model

import sehatq.android.products.data.HomeAPICallback

interface ProductDataSource {
    fun retrieveProducts(callback: HomeAPICallback)
    fun cancel()
}