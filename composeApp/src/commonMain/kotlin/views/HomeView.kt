package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.ChessBlack
import utils.ChessGray

class HomeView: Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier.fillMaxSize()
            .onPlaced {
                Napier.base(DebugAntilog())

                CoroutineScope(Dispatchers.Default).launch {
                    signIn()
                }
            }
            .background(color = ChessBlack),
            contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource("logo.png"),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )

                Row(modifier = Modifier.padding(top = 30.dp)) {
                    Text("C", color = ChessGray, fontSize = 20.sp)
                    Text("hess", color = Color.White, fontSize = 20.sp)

                    Text(" A", color = ChessGray, fontSize = 20.sp)
                    Text("nd", color = Color.White, fontSize = 20.sp)

                    Text(" C", color = ChessGray, fontSize = 20.sp)
                    Text("hat", color = Color.White, fontSize = 20.sp)
                }

                Button(onClick = {
                    navigator.push(QueueView())
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                    Text("Chess", color = ChessBlack, fontSize = 20.sp)
                }

                Text("OR", color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(top = 30.dp))

                Button(onClick = {

                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 30.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)) {
                    Text("Chat", color = ChessBlack, fontSize = 20.sp)
                }
            }

        }
    }

    suspend fun signIn() {
        val result = Firebase.auth.signInAnonymously()
        currentUser = result.user

        print("thisthis ${currentUser!!.uid}")
    }
}

var currentUser: FirebaseUser? = null