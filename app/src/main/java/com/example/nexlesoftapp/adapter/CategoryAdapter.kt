package com.example.nexlesoftapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.nexlesoftapp.R
import com.example.nexlesoftapp.model.CategoryModel


class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private var categories: ArrayList<CategoryModel>
    private var context: Context

    private var iToggleCategory: IToggleCategory? = null
    private var count = 0
    private var lastCheckedPosition = -1

    fun setIToggleCategory(iToggleCategory: IToggleCategory) {
        this.iToggleCategory = iToggleCategory
    }

    interface IToggleCategory {
        fun sendDataToggleClicked(count: Int, categoryModel: CategoryModel, isDelete: Boolean)
    }

    constructor(categories: ArrayList<CategoryModel>, context: Context) : super() {
        this.categories = categories
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var index = position

        var model = categories[index]

        holder.toggleItemCategory.text = model.name
        holder.toggleItemCategory.textOff = model.name

        //holder.toggleItemCategory.isChecked = lastCheckedPosition == index

        holder.toggleItemCategory.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.toggleItemCategory.textOn = model.name
                count++
                if (iToggleCategory != null) {
                    iToggleCategory!!.sendDataToggleClicked(count, model, false)
                }
            } else {
                holder.toggleItemCategory.textOff = model.name
                count--
                if (iToggleCategory != null) {
                    iToggleCategory!!.sendDataToggleClicked(count, model, true)
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toggleItemCategory: ToggleButton

        init {
            toggleItemCategory = itemView.findViewById<ToggleButton>(R.id.toggleItemCategory)

        }
    }
}