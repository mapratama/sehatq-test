package sehatq.android.products.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import sehatq.android.products.R
import sehatq.android.products.data.AdapterCallback
import sehatq.android.products.model.Product
import sehatq.android.products.view.adapter.SearchProductAdapter
import sehatq.android.products.viewmodel.ProductSearchViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var productSearchViewModel: ProductSearchViewModel
    private lateinit var productAdapter: SearchProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupViewModel()
        setupUI()
    }

    private fun setupUI(){
        productAdapter = SearchProductAdapter(adapterCallback)
        productRecyclerView.layoutManager = LinearLayoutManager(this)
        productRecyclerView.adapter = productAdapter

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                productSearchViewModel.loadProducts(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    private fun setupViewModel(){
        productSearchViewModel = ProductSearchViewModel()

        productSearchViewModel.products.observe(this, renderProducts)
        productSearchViewModel.isEmptyList.observe(this, emptyListObserver)
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
            val intent = Intent(this@SearchActivity, ProductDetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    fun backButtonOnClick(view: View) {
        finish()
    }
}
