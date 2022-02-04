package com.example.fredshopping.Activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.fredshopping.R
import com.example.fredshopping.databinding.ActivityRegisterBinding
import com.example.fredshopping.model.User
import com.example.fredshopping.utils.Constants
import com.example.fredshopping.utils.SvelteTrialBold
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.thunder.apps.myshoppal.activities.UserProfileActivity

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()


        val tv_login= findViewById<SvelteTrialBold>(R.id.tv_login).setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnRegister.setOnClickListener{
            registerUser()
        }

    }
    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarRegisterActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_black_arrow)
        binding.toolbarRegisterActivity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
    fun userRegistrationSuccess() {
        hideProgressDialog()
        Toast.makeText(this@RegisterActivity, "You are registered successfully", Toast.LENGTH_SHORT)
            .show()
    }
    fun userLoggedInSuccess(user: User) {
        hideProgressDialog()
        val intent = Intent(this@RegisterActivity, UserProfileActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
        startActivity(intent)
        finish()
    }
    private fun registerUser() {
        if (validateRegisterDetails()) {
            showProgressDialog(getString(R.string.please_wait))

            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        hideProgressDialog()
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        showErrorSnackBar(
                            "Registration Successful, your user id is ${firebaseUser.uid}",
                            false
                        )
                        FirebaseAuth.getInstance().signOut()
                        finish()
//                        val userId = firebaseUser.uid

//                        val user = User(userId,
//                            binding.etFirstName.text.toString().trim { it <= ' ' },
//                            binding.etLastName.text.toString().trim { it <= ' ' },
//                            binding.etEmail.text.toString().trim { it <= ' ' })
//
//                        //Register the user to the FireStore firebase
//                        FireStoreClass().registerUser(this, user)
//                        //getting details and logged in the user
//                        FireStoreClass().getUserDetails(this@RegisterActivity)
//                    } else {
//                        hideProgressDialog()
//                        Toast.makeText(
//                            this@RegisterActivity,
//                            task.exception!!.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }.addOnFailureListener { exception ->
//                    hideProgressDialog()
//                    Toast.makeText(this@RegisterActivity, exception.message, Toast.LENGTH_SHORT)
//                        .show()
//                }
                        } else{
            showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter first name.", true)
                false
            }
            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter last name.", true)
                false
            }
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter an email id.", true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter a password.", true)
                false
            }
            TextUtils.isEmpty(binding.etConfirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar("Please enter confirm password.", true)
                false
            }
            binding.etPassword.text.toString()
                .trim { it <= ' ' } != binding.etConfirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar("Password and confirm password does not match", true)
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar("Please agree terms and conditions.", true)
                false
            }
            else -> {
                true
            }

        }
    }

}