package com.rvv.android.test.taks.lowkey.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rvv.android.test.taks.lowkey.ui.common.LinearSpaceItemDecoration

fun RecyclerView.addLinearSpaceItemDecoration(
    dp: Int,
    showFirstVerticalDivider: Boolean = false,
    showLastVerticalDivider: Boolean = false,
    showFirstHorizontalDivider: Boolean = false,
    showLastHorizontalDivider: Boolean = false,
    shouldDecorate: (parent: RecyclerView, child: View) -> Boolean = { _, _ -> true },
) {
    addItemDecoration(
        LinearSpaceItemDecoration(
            context.dpToPxInt(dp),
            showFirstVerticalDivider,
            showLastVerticalDivider,
            showFirstHorizontalDivider,
            showLastHorizontalDivider,
            shouldDecorate
        )
    )
}
