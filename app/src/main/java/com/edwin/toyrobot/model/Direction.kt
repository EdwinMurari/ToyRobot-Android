package com.edwin.toyrobot.model

enum class Direction{
    NORTH,
    SOUTH,
    WEST,
    EAST;

    fun left(): Direction{
        return when(this){
            NORTH -> WEST
            SOUTH -> EAST
            WEST -> SOUTH
            EAST -> NORTH
        }
    }

    fun right(): Direction{
        return when(this){
            NORTH -> EAST
            SOUTH -> WEST
            WEST -> NORTH
            EAST -> SOUTH
        }
    }
}
