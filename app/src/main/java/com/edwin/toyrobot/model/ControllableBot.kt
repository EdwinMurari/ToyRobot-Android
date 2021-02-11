package com.edwin.toyrobot.model

interface ControllableBot {
    fun place(pose: Pose)
    fun move()
    fun left()
    fun right()
    fun report(): String
}