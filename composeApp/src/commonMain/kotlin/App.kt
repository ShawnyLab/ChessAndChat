import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import models.ChessRoom
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.ChessBlack
import utils.ChessGray
import views.HomeView

@OptIn(InternalVoyagerApi::class)
@Composable
fun App() {
    MaterialTheme {
        Navigator(HomeView())


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

suspend fun addRoom(): String {
    val db = Firebase.firestore

    val room = hashMapOf(
        "white" to "user1",
        "black" to "user2"
    )

    return db.collection("chessRooms").add(room).id
}

suspend fun getRooms(): List<ChessRoom> {
    val db = Firebase.firestore




    val querySnapshot = db.collection("chessRooms").get()
    return querySnapshot.documents.map {
        val white: String = it.get("white")
        val black: String = it.get("black")

        ChessRoom(it.id, white, black)
    }
}