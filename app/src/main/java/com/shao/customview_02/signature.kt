package com.shao.customview_02

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.util.*
import kotlin.collections.HashMap

class signature(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val lines: LinkedList<LinkedList<HashMap<String, Float>>> =
        LinkedList<LinkedList<HashMap<String, Float>>>()
    var initFlag = false
    val mPaint0: Paint = Paint()

    init {
        setBackgroundColor(Color.LTGRAY)

//        setOnClickListener(
//            OnClickListener {
//                Log.d("Shao", "click");
//            }
//        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!initFlag) {
            initialize()
            initFlag = true
        }


        for (line in lines) {
            for (i in 1 until line.size) {
                val p0: HashMap<String, Float> = line[i - 1]
                val p1: HashMap<String, Float> = line[i]

                canvas?.drawLine(
                    p0.get("x")!!, p0.get("y")!!,
                    p1.get("x")!!, p1.get("y")!!, mPaint0
                )
            }
        }

    }

    private fun initialize() {
        mPaint0.color = Color.BLACK
        mPaint0.strokeWidth = 15f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d("Shao", "Down")
                setFirstPoint(event)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("Shao", "Move")
                setMovePoint(event)
            }
            MotionEvent.ACTION_UP -> {
                Log.d("Shao", "Up")
                setEndPoint(event)
            }
        }
        return true
    }

    private fun setFirstPoint(event: MotionEvent) {
        val line: LinkedList<HashMap<String, Float>> = LinkedList<HashMap<String, Float>>()
        val ex: Float = event.x
        val ey: Float = event.y
        val mPoint: HashMap<String, Float> = HashMap<String, Float>()
        mPoint.put("x", ex)
        mPoint.put("y", ey)
        line.add(mPoint)
        lines.add(line)
        invalidate()
    }

    private fun setMovePoint(event: MotionEvent) {
        val ex: Float = event.x
        val ey: Float = event.y
        val mPoint: HashMap<String, Float> = HashMap<String, Float>()
        mPoint.put("x", ex)
        mPoint.put("y", ey)
        lines.last.add(mPoint)
        invalidate()
    }

    private fun setEndPoint(event: MotionEvent) {
        val ex: Float = event.x
        val ey: Float = event.y
        val mPoint: HashMap<String, Float> = HashMap<String, Float>()
        mPoint.put("x", ex)
        mPoint.put("y", ey)
        lines.last.add(mPoint)
        invalidate()
    }

    fun clear() {
        lines.clear()
        invalidate()
    }

    fun undo() {
        if (lines.size > 0) {
            lines.removeLast()
            invalidate()
        }
    }


}