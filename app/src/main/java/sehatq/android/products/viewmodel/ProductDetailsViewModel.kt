package sehatq.android.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sehatq.android.products.model.AppDatabase
import sehatq.android.products.model.Product
import sehatq.android.products.model.Purchase

class ProductDetailsViewModel(val productID: Int): ViewModel() {

    private val dao = AppDatabase.getInstance().appDao()

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _isSuccessBuy = MutableLiveData<Boolean>()
    val isSuccessBuy: LiveData<Boolean> = _isSuccessBuy

    init {
        _product.postValue(dao.getProduct(productID))
    }

    fun buyProduct() {
        dao.insertPurchase(
                Purchase(product_id = _product.value!!.id)
        )
        _isSuccessBuy.postValue(true)
    }

    fun setIsLovedProduct() {
        val isLoved = if (_product.value!!.isLoved == 0) 1 else 0
        val dao = AppDatabase.getInstance().appDao()
        dao.setFavoriteProduct(productID, isLoved)
        _product.postValue(dao.getProduct(productID))
    }
}