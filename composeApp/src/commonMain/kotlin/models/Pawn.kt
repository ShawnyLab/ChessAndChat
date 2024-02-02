package models
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.Side

open class Pawn(side: Side): Piece(side, 0) {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Image() {
        return when (side) {
            Side.BLACK -> Image(
                painter = painterResource("pawn_black.png"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 10.dp)
            )
            Side.WHITE -> Image(
                painter = painterResource("pawn_white.png"),
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

        when (side) {
            Side.WHITE -> {
                if (moved == 0) {
                    if (pieces[x-2][y] == null && pieces[x-1][y] == null) {
                        paths += Pair(x-2, y)
                        paths += Pair(x-1, y)
                    } else if (pieces[x-1][y] == null) {
                        paths += Pair(x-1, y)
                    }
                } else {
                    if (pieces[x-1][y] == null) {
                        paths += Pair(x-1, y)
                    }
                }

                if (y != 0 && pieces[x-1][y-1] != null && pieces[x-1][y-1]!!.side != side) {
                    paths += Pair(x-1, y-1)
                }

                if (y != 7 && pieces[x-1][y+1] != null && pieces[x-1][y+1]!!.side != side) {
                    paths += Pair(x-1, y+1)
                }
            }
            Side.BLACK -> {
                if (moved == 0) {
                    if (pieces[x+2][y] == null && pieces[x+1][y] == null) {
                        paths += Pair(x+2, y)
                        paths += Pair(x+1, y)
                    } else if (pieces[x+1][y] == null) {
                        paths += Pair(x+1, y)
                    }
                } else {
                    if (pieces[x+1][y] == null) {
                        paths += Pair(x+1, y)
                    }
                }

                if (y != 0 && pieces[x+1][y-1] != null && pieces[x+1][y-1]!!.side != side) {
                    paths += Pair(x+1, y-1)
                }

                if (y != 7 && pieces[x+1][y+1] != null && pieces[x+1][y+1]!!.side != side) {
                    paths += Pair(x+1, y+1)
                }
            }
        }

        return paths
    }
}