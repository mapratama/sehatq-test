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

class SearchProductAdapter(var adapterCallback: AdapterCallback): RecyclerView.Adapter<SearchProductAdapter.ViewHolder>() {

    private var products: List<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_product_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product= products[position]

        viewHolder.id = product.id
        viewHolder.titleTextView.text= product.title
        viewHolder.priceTextView.text= product.price
        Glide.with(viewHolder.imageView.context).load(product.imageUrl).into(viewHolder.imageView)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun update(data: List<Product>) {
        products = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var id = 0
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            adapterCallback.onClick(id)
        }
    }
}