package sehatq.android.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sehatq.android.products.model.AppDatabase
import sehatq.android.products.model.Product
import sehatq.android.products.model.Purchase


class PurchaseViewModel: ViewModel() {

    private val _products = MutableLiveData<List<Product>>().apply { value = emptyList() }
    val products: LiveData<List<Product>> = _products

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadData() {
        val dao = AppDatabase.getInstance().appDao()
        val purchases = dao.selectAllPurchase()
        if (purchases.size == 0)
            _isEmptyList.postValue(true)
        else
            _products.postValue(purchases.map { dao.getProduct(it.product_id) })
    }
}