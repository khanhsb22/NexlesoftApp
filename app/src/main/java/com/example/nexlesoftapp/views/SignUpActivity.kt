package com.example.nexlesoftapp.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.nexlesoftapp.R
import com.example.nexlesoftapp.model.UserModel
import com.example.nexlesoftapp.util.DaggerMainComponent
import com.example.nexlesoftapp.util.SetupConfig
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject


open class SignUpActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    @Inject
    lateinit var setupConfig: SetupConfig

    private lateinit var progressBarCheckPass: ProgressBar
    private lateinit var pbLoading: ProgressBar
    private lateinit var toggleHideShowPass: ToggleButton
    private lateinit var rlNotify: RelativeLayout
    private lateinit var tvCheckLevel: TextView
    private lateinit var tvTitleEmail: TextView
    private lateinit var tvOK: TextView
    private lateinit var btnNext: FrameLayout
    private lateinit var tvTitlePassword: TextView
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var tvMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        makeStatusBarTransparent()
        initViews()
        initDaggerComponent()
        handleWithViews()

    }

    private fun initDaggerComponent() {
        DaggerMainComponent.create().poke(this@SignUpActivity)
    }

    private fun initViews() {
        progressBarCheckPass = findViewById(R.id.progressBarCheckPass)
        pbLoading = findViewById(R.id.pbLoading)
        toggleHideShowPass = findViewById(R.id.toggleHideShowPass)
        rlNotify = findViewById(R.id.rlNotify)
        tvCheckLevel = findViewById(R.id.tvCheckLevel)
        tvTitleEmail = findViewById(R.id.tvTitleEmail)
        tvOK = findViewById(R.id.tvOK)
        btnNext = findViewById(R.id.btnNext)
        tvTitlePassword = findViewById(R.id.tvTitlePassword)
        edtEmail = findViewById(R.id.edtEmail)
        edtPass = findViewById(R.id.edtPass)
        tvMessage = findViewById(R.id.tvMessage)
    }

    private fun handleWithViews() {
        progressBarCheckPass.progressDrawable.setColorFilter(
            resources.getColor(R.color.blue),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        tvCheckLevel.visibility = View.GONE

        tvTitleEmail.visibility = View.GONE
        tvTitlePassword.visibility = View.GONE

        edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvTitleEmail.visibility = View.VISIBLE
                if (s!!.isEmpty()) {
                    tvTitleEmail.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        edtPass.addTextChangedListener(object : TextWatcher {
            var text = ""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvTitlePassword.visibility = View.VISIBLE
                if (s!!.isEmpty()) {
                    tvTitlePassword.visibility = View.GONE
                    tvCheckLevel.visibility = View.GONE
                    progressBarCheckPass.progressDrawable.setColorFilter(
                        resources.getColor(
                            R.color.blue
                        ),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
                text = s.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                checkPasswordLevel(text)
            }
        })

        toggleHideShowPass.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                edtPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                edtPass.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        rlNotify.visibility = View.GONE

        tvOK.setOnClickListener {
            rlNotify.visibility = View.GONE
        }

        btnNext.setOnClickListener {
            if (checkValidInput()) {
                pbLoading.visibility = View.VISIBLE
                rlNotify.visibility = View.GONE
                var viewModel =
                    setupConfig.initViewModel(this@SignUpActivity, setupConfig.setupNetwork())
                var textEmail = edtEmail.text.toString().trim()
                var textPassword = edtPass.text.toString().trim()
                val userModel = UserModel("testbbb", "bbbbb", textEmail, textPassword)
                viewModel.signupUser(userModel).observe(this, Observer { signupResponse ->
                    if (signupResponse != null) {
                        Log.d(TAG, "handleWithViews: Response signup: $signupResponse")
                        pbLoading.visibility = View.GONE
                        val intent = Intent(this@SignUpActivity, CategoryActivity::class.java)
                        var token = signupResponse.token
                        intent.putExtra("token", token)
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "handleWithViews: Error signup !")
                        pbLoading.visibility = View.GONE
                    }
                })

            }
        }
    }

    private fun checkPasswordLevel(text: String) {
        if (text.isEmpty()) {
            tvCheckLevel.visibility = View.GONE
            progressBarCheckPass.progressDrawable.setColorFilter(
                resources.getColor(R.color.blue),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            return
        }
        tvCheckLevel.visibility = View.VISIBLE

        var charArray = text.toCharArray()

        var countUpperCase = 0
        var countLowerCase = 0
        var haveOneNumber = false
        val pattern: Pattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
        var matcher: Matcher
        var haveOneSpecialCharacter = false

        var passLevel0 = false
        var passLevel1 = false
        var passLevel2 = false
        var passLevel3 = false
        var passLevel4 = false

        for (i in charArray.indices) {

            if (Character.isUpperCase(charArray[i])) {
                countUpperCase++
            }
            if (Character.isLowerCase(charArray[i])) {
                countLowerCase++
            }
            if (Character.isDigit(charArray[i])) {
                haveOneNumber = true
            }
            matcher = pattern.matcher(charArray[i].toString())
            if (matcher.find()) {
                haveOneSpecialCharacter = true
            }
        }
        if (charArray.size in 0..5) {
            passLevel0 = true
        }
        if (charArray.size in 6..18) {
            if (haveOneSpecialCharacter && (countUpperCase > 0 && countLowerCase > 0) && haveOneNumber) {
                passLevel4 = true
            }
            if (haveOneNumber && (countUpperCase > 0 && countLowerCase > 0)) {
                passLevel3 = true
            }
            if (countUpperCase > 0 && countLowerCase > 0) {
                passLevel2 = true
            }
            if (countUpperCase == charArray.size) {
                passLevel1 = true
            }
            if (countLowerCase == charArray.size) {
                passLevel1 = true
            }
        }

        if (passLevel0) {
            tvCheckLevel.text = "Too short"
            tvCheckLevel.setTextColor(Color.parseColor("#86868E"))
            progressBarCheckPass.progress = 0
            progressBarCheckPass.progressDrawable.setColorFilter(
                Color.parseColor("#FFFFFF"),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        if (passLevel1) {
            tvCheckLevel.text = "Week"
            tvCheckLevel.setTextColor(Color.parseColor("#E05151"))
            progressBarCheckPass.progress = 25
            progressBarCheckPass.progressDrawable.setColorFilter(
                Color.parseColor("#E05151"),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        if (passLevel2) {
            tvCheckLevel.text = "Fair"
            tvCheckLevel.setTextColor(Color.parseColor("#E3A063"))
            progressBarCheckPass.progress = 50
            progressBarCheckPass.progressDrawable.setColorFilter(
                Color.parseColor("#E3A063"),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        if (passLevel3) {
            tvCheckLevel.text = "Good"
            tvCheckLevel.setTextColor(Color.parseColor("#647FFF"))
            progressBarCheckPass.progress = 75
            progressBarCheckPass.progressDrawable.setColorFilter(
                Color.parseColor("#647FFF"),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        if (passLevel4) {
            tvCheckLevel.text = "Strong"
            tvCheckLevel.setTextColor(Color.parseColor("#91E2B7"))
            progressBarCheckPass.progress = 100
            progressBarCheckPass.progressDrawable.setColorFilter(
                Color.parseColor("#91E2B7"),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
    }

    private fun checkValidInput(): Boolean {
        var textEmail = edtEmail.text.toString()
        var textPassword = edtPass.text.toString()

        if (textEmail.trim() == "" && textPassword.trim() == "") {
            showWarningMessage("Email and Password cannot empty !")
            return false
        }
        if (textEmail.trim() == "") {
            showWarningMessage("Email cannot empty !")
            return false
        }
        if (textPassword.trim() == "") {
            showWarningMessage("Password cannot empty !")
            return false
        }
        if (!isValidEmail(textEmail)) {
            showWarningMessage("Your Email is not valid !")
            return false
        }
        if ((textPassword.trim().length < 6 && textPassword.trim() != "") || textPassword.trim().length > 18) {
            showWarningMessage("The password must be between 6-18 characters !")
            return false
        }
        return true
    }

    private fun showWarningMessage(message: String) {
        tvMessage.text = message
        rlNotify.visibility = View.VISIBLE
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun makeStatusBarTransparent() {
        val w: Window = window
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}