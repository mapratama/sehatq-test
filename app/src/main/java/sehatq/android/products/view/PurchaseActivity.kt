package sehatq.android.products.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_purchase.*
import sehatq.android.products.R
import sehatq.android.products.data.AdapterCallback
import sehatq.android.products.model.Product
import sehatq.android.products.view.adapter.SearchProductAdapter
import sehatq.android.products.viewmodel.PurchaseViewModel

class PurchaseActivity : AppCompatActivity() {

    private lateinit var purchaseViewModel: PurchaseViewModel
    private lateinit var productAdapter: SearchProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)

        setupViewModel()
        setupUI()
    }

    private fun setupUI(){
        productAdapter = SearchProductAdapter(adapterCallback)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = productAdapter
    }

    private fun setupViewModel(){
        purchaseViewModel = PurchaseViewModel()

        purchaseViewModel.products.observe(this, renderProducts)
        purchaseViewModel.isEmptyList.observe(this, emptyListObserver)
    }

    private val renderProducts = Observer<List<Product>> {
        layoutEmpty.visibility= View.GONE
        productAdapter.update(it)
    }


    private val emptyListObserver= Observer<Boolean> {
        layoutEmpty.visibility = View.VISIBLE
    }

    private val adapterCallback = object: AdapterCallback {
        override fun favIconOnClick(id: Int, isLoved: Int) {}

        override fun onClick(id: Int) {
            val intent = Intent(this@PurchaseActivity, ProductDetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    fun backButtonOnClick(view: View) {
        finish()
    }

    override fun onResume() {
        super.onResume()
        purchaseViewModel.loadData()
    }
}
