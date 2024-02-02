import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.dp
import models.Bishop
import models.Knight
import models.Pawn
import models.Piece
import models.Rook
import utils.Side
import utils.TileDark
import utils.TileLight

@Composable
fun ChessBoard(turn: MutableState<Side>) {
    val chessBoardSize = 8
    val columns = listOf("a", "b", "c" ,"d", "e", "f", "g", "h")
    var selectedTile: Pair<Int, Int>? by remember { mutableStateOf(null) }
    var path = remember { mutableStateOf(Array(8) { Array(8) { false } }) }

    val pieces = remember {
        mutableStateOf(getDefaultPieces())
    }

    fun refreshRoot() {
        path.value = Array(8) { Array(8) { false } }
    }

    Row(modifier = Modifier.onPlaced {

    }) {
        repeat(chessBoardSize) { row ->
            Column(modifier = Modifier
                .weight(1f)
                .aspectRatio(0.125f)) {
                repeat(chessBoardSize) { column ->
                    Box(modifier = Modifier
                        .background(if ((column % 2 == 0 && row % 2 == 0) || column % 2 == 1 && row % 2 == 1) TileLight else TileDark)
                        .aspectRatio(1.0f)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { _ ->
                                    refreshRoot()

                                    pieces.value[column][row]?.let {
                                        if (it.side == turn.value) {
                                            if (selectedTile == Pair(column, row)) {
                                                selectedTile = null
                                            } else {
                                                val coordinates = it.getAvaliableRoot(x = column, y = row, pieces = pieces.value)

                                                for (coordinate in coordinates) {
                                                    path.value[coordinate.first][coordinate.second] = true
                                                }
                                                selectedTile = Pair(column, row)
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    ){
                        if (pieces.value[column][row] != null) {
                            pieces.value[column][row]!!.Image()

                            if (path.value[column][row]) {
                                Box(modifier = Modifier.fillMaxSize()
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onTap = { _ ->
                                                // move
                                                val newPieces = pieces.value
                                                newPieces[column][row] = newPieces[selectedTile!!.first][selectedTile!!.second]
                                                newPieces[selectedTile!!.first][selectedTile!!.second] = null
                                                newPieces[column][row]!!.moved += 1
                                                selectedTile = null
                                                pieces.value = newPieces

                                                if (turn.value == Side.WHITE) {
                                                    turn.value = Side.BLACK
                                                } else {
                                                    turn.value = Side.WHITE
                                                }
                                                refreshRoot()
                                            }
                                        )
                                    }
                                    .background(Color(red = 32, green = 255, blue = 30, 50)))
                            }
                        } else {
                            if (selectedTile != null && path.value[column][row]) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(20.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onTap = { _ ->
                                                    // move
                                                    val newPieces = pieces.value
                                                    newPieces[column][row] = newPieces[selectedTile!!.first][selectedTile!!.second]
                                                    newPieces[selectedTile!!.first][selectedTile!!.second] = null
                                                    newPieces[column][row]!!.moved += 1
                                                    selectedTile = null
                                                    pieces.value = newPieces

                                                    if (turn.value == Side.WHITE) {
                                                        turn.value = Side.BLACK
                                                    } else {
                                                        turn.value = Side.WHITE
                                                    }
                                                    refreshRoot()
                                                }
                                            )
                                        }
                                ) {
                                    Canvas(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        drawCircle(
                                            color = Color.Green,
                                            radius = size.minDimension / 2,
                                            center = Offset(size.width / 2, size.height / 2)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getDefaultPieces(): Array<Array<Piece?>> {
    return arrayOf(
        arrayOf(
            Rook(Side.BLACK),
            Bishop(Side.BLACK),
            Knight(Side.BLACK),
            Pawn(Side.BLACK),
            Pawn(Side.BLACK),
            Knight(Side.BLACK),
            Bishop(Side.BLACK),
            Rook(Side.BLACK)
        ),
        Array(8) { Pawn(Side.BLACK) },
        Array(8) { null },
        Array(8) { null },
        Array(8) { null },
        Array(8) { null },
        Array(8) { Pawn(Side.WHITE) },
        arrayOf(
            Rook(Side.WHITE),
            Bishop(Side.WHITE),
            Knight(Side.WHITE),
            Pawn(Side.WHITE),
            Pawn(Side.WHITE),
            Knight(Side.WHITE),
            Bishop(Side.WHITE),
            Rook(Side.WHITE)
        ),
    )
}