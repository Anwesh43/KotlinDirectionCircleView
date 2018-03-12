package ui.anwesome.com.kotlindirectioncircleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.directioncircleview.DirectionCircleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DirectionCircleView.create(this)
    }
}
