package com.example.nexlesoftapp.util

import androidx.appcompat.app.AppCompatActivity
import com.example.nexlesoftapp.views.CategoryActivity
import com.example.nexlesoftapp.views.SignUpActivity
import dagger.Component

@Component
interface MainComponent {
    fun poke(app: SignUpActivity)
    fun poke(app: CategoryActivity)
}