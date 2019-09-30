package sehatq.android.products.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*
import sehatq.android.products.R
import sehatq.android.products.data.AdapterCallback
import sehatq.android.products.di.Injection
import sehatq.android.products.model.Category
import sehatq.android.products.model.Product
import sehatq.android.products.view.adapter.CategoryAdapter
import sehatq.android.products.view.adapter.ProductAdapter
import sehatq.android.products.viewmodel.ProductListViewModel
import sehatq.android.products.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var productListViewModel: ProductListViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupUI()
    }

    private fun setupUI(){
        productAdapter = ProductAdapter(adapterCallback)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = productAdapter
        productRecyclerView.isNestedScrollingEnabled = false

        categoryAdapter = CategoryAdapter()
        categoryRecyclerView.layoutManager = GridLayoutManager(this,1, GridLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.adapter = categoryAdapter
        categoryRecyclerView.isNestedScrollingEnabled = false

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.profile_tab) {
                startActivity(Intent(this, PurchaseActivity::class.java))
                bottomNavigationView.selectedItemId = R.id.home_tab
            }
            else menuItem.isChecked = true

            false
        }

        bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
    }

    private fun setupViewModel(){
        productListViewModel = ViewModelProviders.of(this, ViewModelFactory(
                Injection.providerRepository())
        ).get(ProductListViewModel::class.java)

        productListViewModel.products.observe(this, renderProducts)
        productListViewModel.categories.observe(this, renderCategories)
        productListViewModel.isViewLoading.observe(this, isViewLoadingObserver)
        productListViewModel.onMessageError.observe(this, onMessageErrorObserver)
        productListViewModel.isEmptyList.observe(this, emptyListObserver)
    }

    private val renderProducts = Observer<List<Product>> {
        layoutError.visibility= View.GONE
        layoutEmpty.visibility= View.GONE
        productAdapter.update(it)
    }

    private val renderCategories = Observer<List<Category>> {
        layoutError.visibility= View.GONE
        layoutEmpty.visibility= View.GONE
        categoryAdapter.update(it)
    }

    private val isViewLoadingObserver = Observer<Boolean> {
        val visibility= if(it) View.VISIBLE else View.GONE
        progressBar.visibility = visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        layoutError.visibility = View.VISIBLE
        layoutEmpty.visibility = View.GONE
        textViewError.text = it.toString()
    }

    private val emptyListObserver= Observer<Boolean> {
        layoutEmpty.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    fun searchOnClick(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    private val adapterCallback = object: AdapterCallback {
        override fun favIconOnClick(id: Int, isLoved: Int) {
            productListViewModel.setIsLovedProduct(id, isLoved)
        }

        override fun onClick(id: Int) {
            val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        productListViewModel.loadProducts()
    }
}
