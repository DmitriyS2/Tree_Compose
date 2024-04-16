package com.sd.tree_compose.activity.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.sd.tree_compose.dto.NodeItem

@Composable
fun ShowListItem(
    listChildren: MutableState<List<NodeItem>>,
    currentChild: MutableState<NodeItem>,
    expandedChild: MutableState<Boolean>
) {
    LazyColumn() {
        itemsIndexed(listChildren.value) { _, item ->
            ShowCardItem(
                child = true,
                padding = 16,
                size = 12,
                currentChildOrParent = currentChild,
                item = item,
                expanded = expandedChild
            )
        }
    }
}