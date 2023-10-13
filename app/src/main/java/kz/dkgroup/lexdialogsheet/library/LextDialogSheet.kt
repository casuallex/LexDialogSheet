package kz.dkgroup.lexdialogsheet.library

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


class LexDialogSheetState(
    val anchors: List<LexDialogAnchor> = LexDialogAnchor.DEFAULT,
    direction: Direction = Direction.Bottom
) {

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
        LexDialogSheetLayout(sheetState = sheetState, modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}