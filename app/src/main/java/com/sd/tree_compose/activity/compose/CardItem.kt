package com.sd.tree_compose.activity.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.tree_compose.dto.NodeItem

@Composable
fun ShowCardItem(
    child: Boolean,
    padding: Int,
    size: Int,
    currentChildOrParent: MutableState<NodeItem>,
    item: NodeItem = NodeItem(),
    expanded: MutableState<Boolean>,
    context: Context? = null
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = if (child) Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                currentChildOrParent.value = item
                expanded.value = true
            } else Modifier
            .fillMaxWidth()
            .clickable {
                if (currentChildOrParent.value.id == 1L) {
                    Toast
                        .makeText(
                            context,
                            "Не получится. Это root-элемент",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    return@clickable
                }
                expanded.value = true
            },

        shape = RoundedCornerShape(10.dp),

        ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(
                    top = padding.dp,
                    bottom = padding.dp
                ),
                text = if (child) item.name else currentChildOrParent.value.name,
                fontSize = size.sp
            )
        }
    }
}
