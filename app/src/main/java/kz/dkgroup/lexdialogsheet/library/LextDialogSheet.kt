package kz.dkgroup.lexdialogsheet.library

import android.util.Log
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun rememberLexDialogSheetState(): LexDialogSheetState {
    return remember {
        LexDialogSheetState()
    }
}

class LexDialogSheetState(
    val anchors: List<LexDialogAnchor> = LexDialogAnchor.DEFAULT,
    val initialAnchor: LexDialogAnchor = LexDialogAnchor.Hidden,
    val direction: Direction = Direction.Bottom
) {
//
//    internal var swipeableState = SwipeableV2State(
//        initialValue = initialAnchor,
//        animationSpec = SwipeableV2Defaults.AnimationSpec,
//        confirmValueChange = confirmValueChange,
//    )

}

fun LexDialogSheetState.dragDirection() = when (direction) {
    Direction.Bottom -> Orientation.Vertical
    Direction.Top -> Orientation.Vertical
    Direction.Left -> Orientation.Horizontal
    Direction.Right -> Orientation.Horizontal
}

@Composable
fun LexDialogSheet(
    sheetState: LexDialogSheetState,
    pinEnd: @Composable () -> Unit = {},
    pinStart: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val fullHeight = constraints.maxHeight
        val anchorsCalculatedInPx = sheetState.anchors.map {
            when (it) {
                is LexDialogAnchor.Dp -> LexDialogAnchor.Px(with(LocalDensity.current) { it.value.dp.toPx() }.roundToInt())
                is LexDialogAnchor.Px -> it
                is LexDialogAnchor.Percent -> LexDialogAnchor.Px(it.value * fullHeight / 100)
            }
        }.sortedBy { it.value }
        Surface(modifier = Modifier
            .fillMaxSize()
            .draggable(DraggableState {

            }, orientation = sheetState.dragDirection(), onDragStopped = { velocity ->
                Log.e("LDSScrollConnection", "velocity $velocity")
            })
            .nestedScroll(
                remember(sheetState) {
                    LexNestedScrollConnection()
                }
            )) {
            Column {
                Box {
                    pinStart()
                }
                content()
                Box {
                    pinEnd()
                }
            }
        }
    }
}

class LexNestedScrollConnection : NestedScrollConnection {
    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        val velocity = super.onPostFling(consumed, available)
        Log.e("LDSScrollConnection", "velocity fling $velocity")
        return velocity
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        val offset = super.onPostScroll(consumed, available, source)
        Log.e("LDSScrollConnection", "offset $offset")
        return offset
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        val velocity = super.onPreFling(available)
        Log.e("LDSScrollConnection", "velocity $velocity")
        return velocity
    }

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val offset = super.onPreScroll(available, source)
        Log.e("LDSScrollConnection", "offset scrollD $offset")
        return offset
    }
}