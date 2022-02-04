package com.example.fredshopping.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.fredshopping.R
import com.example.fredshopping.databinding.ActivityLoginBinding
import com.example.fredshopping.firestore.FireStoreClass
import com.example.fredshopping.utils.Constants.EXTRA_USER_DETAILS
import com.google.android.gms.common.internal.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import com.thunder.apps.myshoppal.activities.UserProfileActivity


class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvRegister.setOnClickListener (this)
        binding.btnLogin.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.tv_forgot_password->{
                    val intent = Intent(this@LoginActivity,ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }
                R.id.btn_login->{
                    loginUser()
                }
                R.id.tv_register->{
                    val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
    private fun validateLoginDetails() : Boolean{
        return when{
            TextUtils.isEmpty(binding.etEmail.text.toString().trim{ it <= ' '}) ->{
                showErrorSnackBar("Please enter an email id.",true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim{ it <= ' '}) ->{
                showErrorSnackBar("Please enter a password.",true)
                false
            }
            else->{
                true
            }

        }
    }
    private fun loginUser() {

        if (validateLoginDetails()) {

            showProgressDialog(getString(R.string.please_wait))

            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FireStoreClass().getUserDetails(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        Toast.makeText(
                            this@LoginActivity,
                            task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener { exception ->
                    hideProgressDialog()
                    Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_SHORT).show()
                }
        }
    }
    fun userLoggedInSuccess(user: com.example.fredshopping.model.User?){
        hideProgressDialog()

        if(user.profileCompleted == 0){
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }else{
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        finish()
    }
    fun userLoggedInSuccess(user : User){
        hideProgressDialog()

        if(user.profileCompleted == 0){
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }else{
            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        finish()
    }
    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()

    }

}