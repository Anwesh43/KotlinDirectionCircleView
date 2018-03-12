package ui.anwesome.com.directioncircleview

/**
 * Created by anweshmishra on 13/03/18.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
class DirectionCircleView(ctx : Context) : View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer : Renderer = Renderer(this)
    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class State(var j : Int = 0, var prevScale : Float = 0f, var dir : Float = 0f, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f)
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
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class DirectionCircle(var i : Int) {
        val state : State = State()
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            paint.style = Paint.Style.STROKE
            paint.color = Color.WHITE
            paint.strokeCap = Paint.Cap.ROUND
            paint.strokeWidth = Math.min(w, h)/50
            val r : Float = Math.min(w,h)/15
            val size : Float = Math.min(w,h)/20
            val x = -r + (w/2 + r) * state.scales[0]
            val oy = h - 3 * r
            val y = oy + (h/2 - (h - oy) * state.scales[2])
            canvas.save()
            canvas.translate(x, y)
            canvas.rotate(-90f * state.scales[1] + 90f * (1 + state.dir))
            canvas.drawCircle(0f, 0f, r, paint)
            val path : Path = Path()
            path.moveTo( -size/2, -size/2)
            path.lineTo( -size/2, size/2)
            path.lineTo( size/2, 0f)
            canvas.drawPath(path,paint)
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : DirectionCircleView) {
        val directionCircle : DirectionCircle = DirectionCircle(0)
        val animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            directionCircle.draw(canvas, paint)
            animator.animate {
                directionCircle.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            directionCircle.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity : Activity) : DirectionCircleView {
            val view : DirectionCircleView = DirectionCircleView(activity)
            activity.setContentView(view)
            return view
        }
    }
}