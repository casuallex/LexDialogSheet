package kz.dkgroup.lexdialogsheet.library

/**
 * LexDialogAnchor is a class that represents the anchor of the LexDialogSheet.
 *
 */
sealed interface LexDialogAnchor {
    companion object {
        val Hidden = Percent(0)
        val PartiallyExpanded = Percent(50)
        val Expanded = Percent(100)

        val DEFAULT = arrayListOf(
            Hidden,
            PartiallyExpanded,
            Expanded
        )
    }

    class Dp(val value: Int) : LexDialogAnchor {

    }

    class Px(val value: Int) : LexDialogAnchor {

    }

    class Percent(val value: Int) : LexDialogAnchor {

    }

}

enum class Direction {
    Left,
    Right,
    Top,
    Bottom

}