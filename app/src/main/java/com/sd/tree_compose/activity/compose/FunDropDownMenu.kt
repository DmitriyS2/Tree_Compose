package com.sd.tree_compose.activity.compose

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun ShowDropDownMenu(
    expanded: MutableState<Boolean>,
    onChangeParent: () -> Unit,
    text: String,
    onDeleteParent: () -> Unit
) {
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(
            onClick = {
                expanded.value = false
                onChangeParent()
            },
            text = {
                Text(text)
            }
        )
        Divider()
        DropdownMenuItem(
            onClick = {
                expanded.value = false
                onDeleteParent()
            },
            text = {
                Text("Удалить")
            }
        )
    }
}
