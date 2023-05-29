package com.example.nexlesoftapp.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.GridView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nexlesoftapp.R
import com.example.nexlesoftapp.adapter.CategoryAdapter
import com.example.nexlesoftapp.model.CategoryModel
import com.example.nexlesoftapp.util.DaggerMainComponent
import com.example.nexlesoftapp.util.SetupConfig
import javax.inject.Inject


class CategoryActivity : AppCompatActivity(), CategoryAdapter.IToggleCategory {
    private val TAG = "CategoryActivity"
    private lateinit var categories: ArrayList<CategoryModel>

    @Inject
    lateinit var setupConfig: SetupConfig

    private lateinit var tvDone: TextView
    private lateinit var imvBack: ImageView
    private lateinit var rvCategory: RecyclerView
    private lateinit var pbLoading: ProgressBar
    private lateinit var categoryAdapter: CategoryAdapter

    companion object {
        private var saveCategoryList = ArrayList<CategoryModel>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        makeStatusBarTransparent()
        initViews()
        initDaggerComponent()
        getCategoriesByToken()
        handleWithViews()
    }

    private fun initDaggerComponent() {
        DaggerMainComponent.create().poke(this@CategoryActivity)
    }

    private fun getToken(): String {
        return intent.getStringExtra("token").toString()
    }

    private fun getCategoriesByToken() {
        pbLoading.visibility = View.VISIBLE
        categories = ArrayList()
        val token = getToken()
        var viewModel = setupConfig.initViewModel(
            this@CategoryActivity,
            setupConfig.setupNetworkWithToken(token)
        )
        viewModel.getCategories(100, 0).observe(this, Observer { categoriesResponse ->
            if (categoriesResponse != null) {
                categoriesResponse.categories?.forEach { item ->
                    Log.d(TAG, "getCategoriesByToken: item: $item")
                    categories.add(item)
                }
                setAdapter()
                pbLoading.visibility = View.GONE
            } else {
                Log.e(TAG, "getCategoriesByToken: Error load categories !")
                pbLoading.visibility = View.GONE
            }
        })
    }

    private fun initViews() {
        tvDone = findViewById(R.id.tvDone)
        imvBack = findViewById(R.id.imvBack)
        rvCategory = findViewById(R.id.rvCategory)
        pbLoading = findViewById(R.id.pbLoading)
        tvDone.visibility = View.GONE
    }

    private fun handleWithViews() {
        imvBack.setOnClickListener {
            finish()
        }
    }

    private fun setDummyData() {
        categories = ArrayList()
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("2", "Item 2"))
        categories.add(CategoryModel("3", "Item 3"))
        categories.add(CategoryModel("4", "Item 4"))
        categories.add(CategoryModel("5", "Item 5"))
        categories.add(CategoryModel("6", "Item 6"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
        categories.add(CategoryModel("1", "Item 1"))
    }

    private fun setAdapter() {
        val manager = GridLayoutManager(this, 3)
        rvCategory.setHasFixedSize(true)
        rvCategory.layoutManager = manager
        categoryAdapter = CategoryAdapter(categories, this@CategoryActivity)
        rvCategory.adapter = categoryAdapter
        categoryAdapter.setIToggleCategory(this@CategoryActivity)
    }

    private fun makeStatusBarTransparent() {
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun sendDataToggleClicked(
        count: Int,
        categoryModel: CategoryModel,
        isDelete: Boolean
    ) {
        Log.d(TAG, "sendDataToggleClicked: count = $count | model: $categoryModel")
        if (isDelete) {
            saveCategoryList.remove(categoryModel)
        } else {
            saveCategoryList.add(categoryModel)
        }
        Log.d(TAG, "sendDataToggleClicked: size: ${saveCategoryList.size}")
        if (count >= 1) {
            tvDone.visibility = View.VISIBLE
            tvDone.setOnClickListener {
                val args = Bundle()
                args.putSerializable("ListSaved", saveCategoryList as java.io.Serializable)
                val intent = Intent(this@CategoryActivity, HomeActivity::class.java)
                intent.putExtra("BUNDLE", args)
                startActivity(intent)
            }
        } else {
            tvDone.visibility = View.GONE
        }
    }
}