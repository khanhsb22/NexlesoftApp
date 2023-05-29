package com.example.nexlesoftapp.views

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nexlesoftapp.R
import com.example.nexlesoftapp.model.CategoryModel


class HomeActivity : AppCompatActivity() {
    private val TAG = "HomeActivity"
    private var result = "You selected items:\n\n"
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getCategoryList()
        initViews()
    }

    private fun initViews() {
        tvResult = findViewById(R.id.tvResult)
        tvResult.text = result
    }

    private fun getCategoryList() {
        val intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        val list: ArrayList<CategoryModel> = args!!.getSerializable("ListSaved") as ArrayList<CategoryModel>
        list.forEach { item ->
            result += "$item\n"
            Log.d(TAG, "getCategoryList: item: $item")
        }

    }
}