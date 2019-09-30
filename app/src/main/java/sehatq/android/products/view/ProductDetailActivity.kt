package sehatq.android.products.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_product_detail.*
import sehatq.android.products.R
import sehatq.android.products.model.Product
import sehatq.android.products.viewmodel.ProductDetailsViewModel
import android.content.Intent


class ProductDetailActivity : AppCompatActivity() {

    private lateinit var productDetailsViewModel: ProductDetailsViewModel
    private lateinit var shareText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        setupViewModel()
    }

    private fun setupViewModel(){
        val productID = intent.getIntExtra("id", 0)
        productDetailsViewModel = ProductDetailsViewModel(productID)
        productDetailsViewModel.product.observe(this, renderProduct)
        productDetailsViewModel.isSuccessBuy.observe(this, successBuyProduct)
    }

    private val renderProduct = Observer<Product> {
        titleTextView.text = it.title
        descriptionTextView.text = it.description
        priceTextView.text = it.price
        Glide.with(this).load(it.imageUrl).into(photo)

        favIcon.setImageResource(
                if (it.isLoved == 0) R.drawable.ic_favorite_outline else R.drawable.ic_favorite_fill
        )

        shareText = "${it.title}\nPrice: ${it.price}\n\n${it.description}"
    }

    private val successBuyProduct = Observer<Boolean> {
        if (it) Toast.makeText(this, resources.getString(R.string.success_buy), Toast.LENGTH_SHORT).show()
    }

    fun buyButtonOnClick(view: View) {
        productDetailsViewModel.buyProduct()
    }

    fun backButtonOnClick(view: View) {
        finish()
    }

    fun favOnClick(view: View) {
        productDetailsViewModel.setIsLovedProduct()
    }

    fun shareIconOnClick(view: View) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, shareText)
        intent.type = "text/plain"
        startActivity(intent)
    }
}
