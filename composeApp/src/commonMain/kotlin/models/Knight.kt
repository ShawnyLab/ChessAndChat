package models
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.Side


class Knight(side: Side): Piece(side, 0) {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Image() {
        when (side) {
            Side.WHITE -> Image(
                painter = painterResource("knight_white.png"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 3.dp)
            )
            Side.BLACK -> Image(
                painter = painterResource("knight_black.png"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 3.dp)
            )
        }
    }

    override fun getAvailableRoot(x: Int, y: Int, pieces: Array<Array<Piece?>>): Array<Pair<Int, Int>> {
        var paths: Array<Pair<Int, Int>> = arrayOf()

        for (add in arrayOf(Pair(1, 2), Pair(2, 1), Pair(1, -2), Pair(2, -1), Pair(-1, 2), Pair(-2, 1), Pair(-1, -2), Pair(-2, -1))) {
            val newX = x + add.first
            val newY = y + add.second

            if (newX < 0 || newX > 7 || newY < 0 || newY > 7) continue

            if (pieces[newX][newY] == null) {
                paths += Pair(newX, newY)
            } else if (pieces[newX][newY] != null && pieces[newX][newY]!!.side != side) {
                paths += Pair(newX, newY)
            }
        }

        return paths
    }
}