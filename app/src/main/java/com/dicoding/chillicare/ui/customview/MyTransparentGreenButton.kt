package com.dicoding.chillicare.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.chillicare.R

class MyTransparentGreenButton(context: Context, attrs: AttributeSet) :
    AppCompatButton(context, attrs) {
    private var   backgroundTransparent : Drawable = ContextCompat.getDrawable(context, R.drawable.bg_btn_green_transparent) as Drawable

    init {
        setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
        setGravity(android.view.Gravity.CENTER)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background =   backgroundTransparent
        setTextColor(ContextCompat.getColor(context, R.color.dark_grey))
    }
}