package com.edwin.toyrobot.model

class ToyRobot(private var currentPose: Pose? = null) : ControllableBot {

    override fun place(pose: Pose) {
        if (pose.position.x > 5 || pose.position.y > 5)
            return

        currentPose = pose
    }

    override fun move() {
        val pose = currentPose ?: return

        if (pose.position.x == 5 && pose.direction == Direction.East)
            return

        if (pose.position.y == 5 && pose.direction == Direction.North)
            return

        if (pose.position.x == 0 && pose.direction == Direction.West)
            return

        if (pose.position.y == 0 && pose.direction == Direction.South)
            return

        when (pose.direction) {
            Direction.North -> pose.position.y++
            Direction.South -> pose.position.y--
            Direction.West -> pose.position.x--
            Direction.East -> pose.position.x++
        }

        currentPose = pose
    }

    override fun left() {
        val pose = currentPose ?: return
        pose.direction = pose.direction.left()
        currentPose = pose
    }

    override fun right() {
        val pose = currentPose ?: return
        pose.direction = pose.direction.right()
        currentPose = pose
    }

    override fun report(): String {
        if(currentPose == null)
            return "Robot not on table."

        return currentPose.toString()
    }
}