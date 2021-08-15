package com.wlk.myblog.wight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi

import androidx.constraintlayout.widget.ConstraintLayout
import com.wlk.myblog.MyApplication
import com.wlk.myblog.R

class RotateLayout: ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    private val p1 = Paint()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas?) {
        // 倾斜度45,上下左右居中
        val w = measuredWidth
        val h = measuredHeight

        p1.isAntiAlias = true
        p1.style = Paint.Style.FILL
        val path = Path()
        path.moveTo(w.toFloat(), (h - h / 4).toFloat())// 此点为多边形的起点
        path.lineTo((w-w/7.5).toFloat(), (h).toFloat())
        path.lineTo(w.toFloat(), h.toFloat())
        path.close() // 使这些点构成封闭的多边形
        canvas?.drawPath(path, p1)

        val p = Paint()
        p.isAntiAlias = true
        p.color = Color.WHITE// 设置白色
        p.textSize = 32F
        canvas?.drawText(text, (w-w/12.5).toFloat(), (h - 10F).toFloat(), p)
        super.onDraw(canvas)
    }

    private var text = ""
    fun setText(text: String) {
        this.text = text
        invalidate()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun setPaintColor(color: Int){
        p1.color = color
        invalidate()
    }

}
