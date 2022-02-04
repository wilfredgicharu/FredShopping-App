package com.example.fredshopping.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class MSPEditText(context: Context, attrt: AttributeSet): AppCompatEditText(context, attrt) {
    init {
        applyFont()
    }
    private fun applyFont(){
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets,"SvelteTrial-Bold.ttf")
        setTypeface(typeface)
    }
}