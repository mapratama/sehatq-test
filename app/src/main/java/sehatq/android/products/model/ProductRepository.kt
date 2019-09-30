package sehatq.android.products.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sehatq.android.products.data.ApiClient
import sehatq.android.products.data.HomeAPICallback
import sehatq.android.products.data.HomeResponse


class ProductRepository: ProductDataSource {

    private var call: Call<List<HomeResponse>>? = null

    override fun retrieveProducts(callback: HomeAPICallback) {
        call = ApiClient.build()?.home()
        call?.enqueue(object: Callback<List<HomeResponse>>{
            override fun onFailure(call: Call<List<HomeResponse>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<HomeResponse>>, response: Response<List<HomeResponse>>) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        val data = it.first().data
                        val dao = AppDatabase.getInstance().appDao()

                        data.productPromo!!.forEach { product ->
                            dao.insertProduct(product)
                        }

                        data.category.forEach {
                            dao.insertCategory(it)
                        }

                        callback.onSuccess()
                    } else {
                        callback.onError("Tidak bisa mengambil data dari server")
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.cancel()
    }
}