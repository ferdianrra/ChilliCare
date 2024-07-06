package com.dicoding.chillicare.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.chillicare.R

class MyTransparentYellowButton(context: Context, attrs: AttributeSet):
     AppCompatButton(context, attrs) {
        private var   backgroundTransparent : Drawable = ContextCompat.getDrawable(context, R.drawable.bg_btn_yellow_transparent) as Drawable

        init {
            setTextColor(ContextCompat.getColor(context, R.color.black))
            setGravity(android.view.Gravity.CENTER)
        }
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            background =   backgroundTransparent
            setTextColor(ContextCompat.getColor(context, R.color.white_bg))
        }
}