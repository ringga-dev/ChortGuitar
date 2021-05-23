package com.ringga.chortguitar.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.data.utils.UserIden
import com.ringga.chortguitar.ui.home.HomeActivity
import com.ringga.chortguitar.ui.home.ListLaguActivity
import com.ringga.chortguitar.ui.viewmodels.UserState
import com.ringga.chortguitar.ui.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        islogin()
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getState().observer(this, Observer {
            handleUiState(it)
        })
        doLogin()

        register.setOnClickListener {
            Intent(this, RegisteActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }

        et_email.addTextChangedListener {
            image.visibility = View.GONE
        }

        et_pass.addTextChangedListener {
            image.visibility = View.GONE
        }
    }

    private fun handleUiState(it: UserState) {
        when (it) {
            is UserState.Reset -> {
                setEmailError(null)
                setPasswordError(null)
            }
            is UserState.Error -> {
                isloding(false)
                toast(it.err)
            }
            is UserState.ShoewToals -> toast(it.message)
            is UserState.Failed -> {
                isloding(false)
                toast(it.message)
            }
            is UserState.Validate -> {
                it.email?.let {
                    setEmailError(it)
                }
                it.name?.let {
                    setPasswordError(it)
                }
            }
            is UserState.Success -> {
                Constants.setToken(this, it.data.token!!,it.data.no_pengenal!! )
                UserIden.setId(this,it.data.no_pengenal!!)

                startActivity(Intent(this, HomeActivity::class.java)).also {
                    finish()
                }
            }
            is UserState.IsLoding -> isloding(it.state)
        }
    }

    private fun setEmailError(err: String?) {
        in_email.error = err
    }

    private fun setPasswordError(err: String?) {
        in_pass.error = err
    }

    private fun isloding(state: Boolean) {
        if (state) {
            register.isEnabled = false
            btn_login.isEnabled = false
            loading.isIndeterminate = true
        } else {
            loading.apply {
                isIndeterminate = false
                progress = 0
            }
            register.isEnabled = true
            btn_login.isEnabled = true
        }
    }

    private fun toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


    private fun doLogin() {
        btn_login.setOnClickListener {
            val email = et_email.text.toString().trim()
            val passw = et_pass.text.toString().trim()
            if (userViewModel.validate(null, email, passw)) {
                userViewModel.login(email, passw)
            }
        }
    }

    private fun islogin(){
//        setEmailError("token = ${Constants.getToken(this)}")
//        setPasswordError("ID = ${Constants.getId(this)}")
        if (!Constants.getToken(this).equals("undefined")){
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }).also { finish() }
        }
    }
}