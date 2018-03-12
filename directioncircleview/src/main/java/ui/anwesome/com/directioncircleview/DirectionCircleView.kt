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
}