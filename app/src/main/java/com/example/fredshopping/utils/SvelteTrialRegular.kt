package com.example.fredshopping.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.jar.Attributes

class SvelteTrialRegular(context: Context, attr: AttributeSet): AppCompatTextView(context, attr ) {
    init {
        applyFont()
    }
    private fun applyFont(){
        val typeface: Typeface=
            Typeface.createFromAsset(context.assets,"SvelteTrial-Regular.ttf")
        setTypeface(typeface)
    }
}