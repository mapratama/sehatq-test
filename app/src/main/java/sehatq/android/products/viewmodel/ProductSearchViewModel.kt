package sehatq.android.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sehatq.android.products.model.AppDatabase
import sehatq.android.products.model.Product


class ProductSearchViewModel: ViewModel() {

    private val allProducts = AppDatabase.getInstance().appDao().selectAllProduct()

    private val _products = MutableLiveData<List<Product>>().apply { value = emptyList() }
    val products: LiveData<List<Product>> = _products

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    init {
        _products.postValue(allProducts)
    }

    fun loadProducts(key: String?) {
        if (allProducts.isEmpty())
            _isEmptyList.postValue(true)
        else {
            _isEmptyList.postValue(false)

            if (key.isNullOrEmpty())
                _products.postValue(allProducts)
            else
                _products.postValue(allProducts.filter { it.title.contains(key.toString(), true) })
        }
    }
}