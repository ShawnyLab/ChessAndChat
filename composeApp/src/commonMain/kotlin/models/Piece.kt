package models

import androidx.compose.runtime.Composable
import utils.Side

abstract class Piece(val side: Side, var moved: Int = 0) {
    @Composable
    abstract fun Image()

    abstract fun getAvaliableRoot(x: Int, y: Int, pieces: Array<Array<Piece?>>): Array<Pair<Int, Int>>
}