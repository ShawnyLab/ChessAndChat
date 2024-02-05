package views

import ChessBoard
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class GamerView(val roomId: String): Screen {

    @Composable
    override fun Content() {
        ChessBoard(id = roomId)
    }
}