package com.example.fredshopping.Activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import com.example.fredshopping.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val tv_app_name= findViewById<TextView>(R.id.tv_app_name)

        @Suppress("Deprecation")
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN

            )
        }
        @Suppress("Deprecation")
        Handler().postDelayed(
            {
                startActivity(Intent(this@Splash, LoginActivity::class.java))
                finish()
            },
            2500
        )

//        val typeface: Typeface= Typeface.createFromAsset(assets,"SvelteTrial-Bold.ttf")
//        tv_app_name.typeface= typeface

    }
}