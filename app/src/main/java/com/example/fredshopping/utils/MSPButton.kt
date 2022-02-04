package com.example.fredshopping.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MSPButton(context: Context, attrt: AttributeSet): AppCompatButton(context, attrt) {

    init {
        applyFont()
    }
    private fun applyFont(){
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets,"SvelteTrial-Bold.ttf")
        setTypeface(typeface)
    }
}