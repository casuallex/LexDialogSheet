package kz.dkgroup.lexdialogsheet.library

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun LexDialogSheetLayout(
    modifier: Modifier,
    sheetState: LexDialogSheetState,
    content: @Composable () -> Unit
) {
    Layout(content, modifier) { measurables, constraints ->
        val placeable0 = measurables[0].measure(constraints)
        val placeable2 = measurables[2].measure(
            constraints.copy(
                maxWidth = constraints.maxWidth,
                maxHeight = constraints.maxHeight - placeable0.measuredHeight
            )
        )
        val placeable1 = measurables[1].measure(
            constraints.copy(
                maxWidth = constraints.maxWidth,
                maxHeight = constraints.maxHeight - placeable0.measuredHeight - placeable2.measuredHeight
            )
        )
        val sum = placeable0.measuredHeight + placeable1.measuredHeight + placeable2.measuredHeight
        layout(
            constraints.maxWidth,
            sum.coerceAtMost(constraints.maxHeight)
        ) {
            placeable0.placeRelative(0, 0)
            placeable1.placeRelative(0, placeable0.height)
            val footerTopOffset =
                (constraints.maxHeight / 2).coerceAtMost(sum) - placeable2.height
            placeable2.placeRelative(
                0,
                (constraints.maxHeight - 0
//                        sheetState.requireOffset()

                    .toInt() - placeable2.height).coerceAtLeast(
                    footerTopOffset
                )
            )
        }
    }

}