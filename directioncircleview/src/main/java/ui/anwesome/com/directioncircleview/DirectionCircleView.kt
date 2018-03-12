package ui.anwesome.com.directioncircleview

/**
 * Created by anweshmishra on 13/03/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class DirectionCircleView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class State(var j : Int = 0, var prevScale : Float = 0f, var dir : Float = 0f, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += jDir
                if(j == scales.size || j == -1) {
                    dir = 0f
                    jDir *= -1
                    j += jDir
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }
}