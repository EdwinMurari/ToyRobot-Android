package com.edwin.toyrobot.model

class ToyRobot(private var currentPose: Pose? = null) : ControllableBot {

    override fun place(pose: Pose) {
        if (pose.position.x > 5 || pose.position.y > 5)
            return

        currentPose = pose
    }

    override fun move() {
        val pose = currentPose ?: return

        when {
            pose.position.x == 5 && pose.direction == Direction.EAST -> return
            pose.position.y == 5 && pose.direction == Direction.NORTH -> return
            pose.position.x == 0 && pose.direction == Direction.WEST -> return
            pose.position.y == 0 && pose.direction == Direction.SOUTH -> return
            else -> {
                when (pose.direction) {
                    Direction.NORTH -> pose.position.y++
                    Direction.SOUTH -> pose.position.y--
                    Direction.WEST -> pose.position.x--
                    Direction.EAST -> pose.position.x++
                }

                currentPose = pose
            }
        }

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
        if (currentPose == null)
            return "Robot not on table."

        return currentPose.toString()
    }
}