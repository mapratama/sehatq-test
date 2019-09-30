package sehatq.android.products.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sehatq.android.products.R
import sehatq.android.products.data.AdapterCallback
import sehatq.android.products.model.Product

class ProductAdapter(var adapterCallback: AdapterCallback): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var products: List<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product= products[position]

        viewHolder.product = product
        viewHolder.textViewName.text= product.title
        Glide.with(viewHolder.imageView.context).load(product.imageUrl).into(viewHolder.imageView)

        viewHolder.favIcon.setImageResource(
                if (product.isLoved == 0) R.drawable.ic_favorite_outline else R.drawable.ic_favorite_fill
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun update(data: List<Product>) {
        products = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        lateinit var product: Product
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val favIcon: ImageView = view.findViewById(R.id.favIcon)

        init {
            view.setOnClickListener(this)

            favIcon.setOnClickListener {
                val isLoved = if (product.isLoved == 0) 1 else 0
                adapterCallback.favIconOnClick(product.id, isLoved)
            }
        }

        override fun onClick(v: View?) {
            adapterCallback.onClick(product.id)
        }
    }
}