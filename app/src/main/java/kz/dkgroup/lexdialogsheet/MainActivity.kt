package kz.dkgroup.lexdialogsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.dkgroup.lexdialogsheet.library.LexDialogSheet
import kz.dkgroup.lexdialogsheet.library.rememberLexDialogSheetState
import kz.dkgroup.lexdialogsheet.ui.theme.LexDialogSheetTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LexDialogSheetTheme {
                val focusRequester = FocusRequester()
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                BasicTextField("", {}, modifier = Modifier.focusRequester(focusRequester))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomSheetWithHeaderAndFooter("Android")
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomSheetWithHeaderAndFooter(name: String, modifier: Modifier = Modifier) {
//    val sheetState = rememberModalBottomSheetState()
//
//    LaunchedEffect(Unit) {
//        sheetState.show()
//    }
//    ModalBottomSheet(onDismissRequest = {}, sheetState = sheetState) {
//        Text("Header")
//        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
//            repeat(100) {
//                Text("Body")
//            }
//        }
//        Text("Footer")
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWithHeaderAndFooter(name: String, modifier: Modifier = Modifier) {
    val sheetState = rememberLexDialogSheetState()

    LaunchedEffect(Unit) {
//        sheetState.show()
    }
    LexDialogSheet(sheetState = sheetState) {
        Text("Header")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            repeat(100) {
                Text("Body")
            }
        }
        Text("Footer")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LexDialogSheetTheme {
        BottomSheetWithHeaderAndFooter("Android")
    }
}


@Preview(showBackground = true)
@Composable
fun DraggableSample() {
    LexDialogSheetTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            var moveState = remember {
                mutableFloatStateOf(0f)
            }
            Column(modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    translationY = moveState.floatValue
                }
                .background(Color.Yellow)
                .draggable(
                    state = DraggableState { delta ->
                        println("delta $delta")
                        moveState.floatValue += delta
                    },
                    orientation = Orientation.Vertical,
                    onDragStopped = { velocity ->
                        println("velocity $velocity")
                    }
                )) {
                repeat(100) {
                    Text("Body")
                }
            }
        }
    }
}