package com.edwin.toyrobot.view

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.edwin.toyrobot.R
import com.edwin.toyrobot.model.Direction
import com.edwin.toyrobot.model.Pose
import com.edwin.toyrobot.model.ToyRobot

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toyRobot = ToyRobot()
        toyRobot.place(Pose(Point(5, 5), Direction.East))
        toyRobot.move()
        toyRobot.move()
        toyRobot.left()
        toyRobot.move()
        Log.e("TEST", "Reporting current pose: ${toyRobot.report()}")
    }
}