package pw.gerard.gsmelter.data

import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate

enum class Location(val label: String, val furnace: Area.Rectangular, val bank: Area.Rectangular) {
    AL_KHARID("Al Kharid",
            Area.Rectangular(Coordinate(3274, 3184, 0), Coordinate(3278, 3186, 0)),
            Area.Rectangular(Coordinate(3269, 3168, 0), Coordinate(3271, 3170, 0))) // TODO Change the size of the furnace
}