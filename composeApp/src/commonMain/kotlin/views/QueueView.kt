package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.database.ChildEvent
import dev.gitlive.firebase.database.database
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.ChessBlack
import utils.ChessGray

class QueueView: Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        var opponentId: MutableState<String?> = remember { mutableStateOf(null) }
        val navigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier
            .fillMaxSize()
            .background(ChessBlack)
            .onPlaced {
                CoroutineScope(Dispatchers.Default).launch {
                    checkQueue(opponentId, navigator)
                }
            },
            contentAlignment = Alignment.Center,
            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource("loading.png"),
                    contentDescription = null)

                Row(modifier = Modifier.padding(top = 30.dp)) {
                    Text("L", color = ChessGray, fontSize = 20.sp)
                    Text("ooking", color = Color.White, fontSize = 20.sp)

                    Text(" F", color = ChessGray, fontSize = 20.sp)
                    Text("or", color = Color.White, fontSize = 20.sp)

                    Text(" O", color = ChessGray, fontSize = 20.sp)
                    Text("pponent", color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }

    suspend fun putOnQueue() {
        val ref = Firebase.database.reference()

    }

    suspend fun checkQueue(opponentId: MutableState<String?>, navigator: Navigator) {
        val ref = Firebase.database("https://chessandchat-5f02a-default-rtdb.asia-southeast1.firebasedatabase.app").reference()
        val queueData = ref.child("queue").valueEvents
        queueData.collect {
            if (it.hasChildren) {
                val listOnQueue = it.children
                val opponentData = listOnQueue.first()
                val id = opponentData.key
                if (id != null) {

                    Napier.i("this is my id $id")
                    opponentId.value = id

                    createChessRoom(id, navigator)
                }
            }
        }
    }

    suspend fun createChessRoom(opponentId: String, navigator: Navigator) {
        if (currentUser == null) return

        val room = hashMapOf(
            "white" to currentUser!!.uid,
            "black" to opponentId,
            "turn" to "white"
        )

        val ref = Firebase.database("https://chessandchat-5f02a-default-rtdb.asia-southeast1.firebasedatabase.app").reference()
        val newRoomId = ref.child("chessRooms")
            .push().key

        newRoomId?.let {
            ref.child("chessRooms").child(it)
                .setValue(room)

            navigator.push(GamerView(roomId = it))
        }

    }
}