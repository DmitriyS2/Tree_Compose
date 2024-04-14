package com.sd.tree_compose.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.tree_compose.ui.theme.Green100
import com.sd.tree_compose.ui.theme.Orange100
import com.sd.tree_compose.ui.theme.Tree_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val count = remember {
                mutableStateOf(0)
            }
            val list = remember{
               mutableStateOf(listOf("child 0"))
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Green100)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        color = Orange100,
                        text = "Родитель",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 8.dp,

                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                count.value++
                                val l:MutableList<String> = list.value.toMutableList()
                                l.add("child ${count.value}")
                                list.value = l
                            },
                        shape = RoundedCornerShape(10.dp),

                        ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "Hello ${count.value}",
                                fontSize = 18.sp
                            )
                        }

                    }
                    Text(
                        color = Orange100,
                        text = "Дети",
                        modifier = Modifier.padding(top = 32.dp)
                    )

                    LazyColumn() {
                        itemsIndexed(list.value) { _, item ->
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 8.dp
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                                shape = RoundedCornerShape(10.dp),

                                ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier.padding(12.dp),
                                        text = "child $item",
                                        fontSize = 16.sp
                                    )
                                }

                            }
                        }
                    }

                }
            }
        }
    }
}

