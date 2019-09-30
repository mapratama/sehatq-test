package sehatq.android.products.data

interface HomeAPICallback {
    fun onSuccess()
    fun onError(errorMessage: String?)
}

interface AdapterCallback {
    fun onClick(id: Int)
    fun favIconOnClick(id: Int, isLoved: Int)
}