package models

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.Side


class Bishop(side: Side): Piece(side, 0) {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Image() {
        when (side) {
            Side.WHITE -> Image(
                painter = painterResource("bishop_white.png"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 10.dp)
            )
            Side.BLACK -> Image(
                painter = painterResource("bishop_black.png"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 10.dp)
            )
        }
    }

    override fun getAvaliableRoot(x: Int, y: Int, pieces: Array<Array<Piece?>>): Array<Pair<Int, Int>> {
        var paths: Array<Pair<Int, Int>> = arrayOf()

        for (time in arrayOf(Pair(1, 1), Pair(1, -1), Pair(-1, 1), Pair(-1, -1))) {
            for (add in arrayOf(1, 2, 3, 4, 5, 6, 7).map { i -> Pair(i, i)}) {
                val newX = x + add.first * time.first
                val newY = y + add.second * time.second

                if (newX < 0 || newX > 7 || newY < 0 || newY > 7) break

                if (pieces[newX][newY] == null) {
                    paths += Pair(newX, newY)
                } else if (pieces[newX][newY] != null && pieces[newX][newY]!!.side != side) {
                    paths += Pair(newX, newY)
                    break
                } else {
                    break
                }
            }
        }

        return paths
    }
}