import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import models.Pawn
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.Side

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        val turn = remember { mutableStateOf(Side.WHITE) }

        Row {
            ChessBoard(turn)

            Text("$turn.name")
        }

//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

//            AnimatedVisibility(showContent) {
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                    Image(painterResource("compose-multiplatform.xml"), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }

    }
}

suspend fun addUser(): DocumentSnapshot {
    val db = Firebase.firestore

    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815,
    )

    return db.collection("users")
        .add(user)
        .get()
}