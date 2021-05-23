package com.ringga.chortguitar.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ringga.chortguitar.R
import com.ringga.chortguitar.data.utils.Constants
import com.ringga.chortguitar.data.utils.UserIden
import com.ringga.chortguitar.ui.home.HomeActivity
import com.ringga.chortguitar.ui.home.ProfileActivity
import com.ringga.chortguitar.ui.viewmodels.UserState
import com.ringga.chortguitar.ui.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_registe.*

class RegisteActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registe)

        userViewModel =ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.getState().observer(this, Observer {
            handleUiState(it)
        })
        doRegister()
        btn_save.setOnClickListener {
            Intent(this, ProfileActivity::class.java).also {
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
        et_name.addTextChangedListener {
            image.visibility = View.GONE
        }
    }

    private fun doRegister(){
        btn_save.setOnClickListener {
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val passw = et_pass.text.toString().trim()
            if (userViewModel.validate(name, email, passw)){
                userViewModel.register(name,email,passw)
            }
        }
    }

    private fun handleUiState(it: UserState) {
        when (it) {
            is UserState.Reset -> {
                setNameError(null)
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
                it.name?.let {
                    setNameError(it)
                }
                it.email?.let {
                    setEmailError(it)
                }
                it.name?.let {
                    setPasswordError(it)
                }
            }
            is UserState.Success -> {
                Constants.setToken(this, "Bearer${it.data.token}","Bearer${it.data.no_pengenal}")
                UserIden.setId(this, "Bearer${it.data.no_pengenal}")
                startActivity(Intent(this, HomeActivity::class.java)).also {
                    finish()
                }
            }
            is UserState.IsLoding -> isloding(it.state)
        }
    }

    private fun setNameError(err: String?) {
        in_name.error = err
    }

    private fun setEmailError(err: String?) {
        in_email.error = err
    }

    private fun setPasswordError(err: String?) {
        in_pass.error = err
    }

    private fun isloding(state: Boolean) {
        if (state) {
            btn_save.isEnabled = false
            loading.isIndeterminate = true
        } else {
            loading.apply {
                isIndeterminate = false
                progress = 0
            }
            btn_save.isEnabled = true
        }
    }

    private fun toast(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}