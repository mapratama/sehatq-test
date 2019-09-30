package sehatq.android.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sehatq.android.products.data.HomeAPICallback
import sehatq.android.products.model.AppDatabase
import sehatq.android.products.model.Category
import sehatq.android.products.model.Product
import sehatq.android.products.model.ProductDataSource

class ProductListViewModel(private val repository: ProductDataSource): ViewModel() {

    private val _products = MutableLiveData<List<Product>>().apply { value = emptyList() }
    val products: LiveData<List<Product>> = _products

    private val _categories = MutableLiveData<List<Category>>().apply { value = emptyList() }
    val categories: LiveData<List<Category>> = _categories

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadProducts() {
        _isViewLoading.postValue(true)

        val dao = AppDatabase.getInstance().appDao()
        val products = dao.selectAllProduct()
        val categories = dao.selectAllCategory()

        if (products.size > 0 && categories.size > 0) {
            _isViewLoading.postValue(false)
            _products.postValue(products)
            _categories.postValue(categories)
            return
        }

        repository.retrieveProducts(object: HomeAPICallback{
            override fun onError(errorMessage: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(errorMessage)
            }

            override fun onSuccess() {
                _isViewLoading.postValue(false)
                _products.postValue(dao.selectAllProduct())
                _categories.postValue(dao.selectAllCategory())
            }
        })
    }

    fun setIsLovedProduct(productID: Int, isLoved: Int) {
        val dao = AppDatabase.getInstance().appDao()
        dao.setFavoriteProduct(productID, isLoved)
        _products.postValue(dao.selectAllProduct())
    }
}