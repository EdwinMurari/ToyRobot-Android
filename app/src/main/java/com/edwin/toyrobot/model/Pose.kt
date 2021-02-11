package com.edwin.toyrobot.model

import android.graphics.Point

data class Pose(var position: Point, var direction: Direction) {
    override fun toString(): String {
        return "${position.x}, ${position.y}, $direction"
    }
}