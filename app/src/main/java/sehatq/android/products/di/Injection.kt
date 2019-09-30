package sehatq.android.products.di

import sehatq.android.products.model.ProductDataSource
import sehatq.android.products.model.ProductRepository

object Injection {

    fun providerRepository(): ProductDataSource{
        return ProductRepository()
    }
}