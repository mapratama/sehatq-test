package sehatq.android.products.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sehatq.android.products.R
import sehatq.android.products.model.Category

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categories: List<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = categories[position]

        viewHolder.textViewName.text = category.name
        Glide.with(viewHolder.imageView.context).load(category.imageUrl).into(viewHolder.imageView)
    }

    override fun getItemCount(): Int {
        Log.e("######", categories.size.toString())
        return categories.size
    }

    fun update(data: List<Category>) {
        categories = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }
}