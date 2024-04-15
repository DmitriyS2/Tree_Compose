package com.sd.tree_compose.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sd.tree_compose.R
import com.sd.tree_compose.dto.NodeItem
import com.sd.tree_compose.ui.theme.Green100
import com.sd.tree_compose.ui.theme.Orange100
import com.sd.tree_compose.ui.theme.Tree_ComposeTheme
import com.sd.tree_compose.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tree_ComposeTheme {
                var currentChild = NodeItem()

                val expandedParent = remember {
                    mutableStateOf(false)
                }
                val expandedChild = remember {
                    mutableStateOf(false)
                }

                val listChildren = remember {
                    mutableStateOf(listOf(NodeItem()))
                }
                val parentNow = remember {
                    mutableStateOf(NodeItem())
                }

                viewModel.dataChildren.observe(this) {
                    listChildren.value = it
                }
                viewModel.currentParent.observe(this) {
                    parentNow.value = it
                }
                viewModel.maxId.observe(this) {
                    viewModel.doHash()
                }

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            content = {
                                Icon(Icons.Filled.Add, contentDescription = "Добавить")
                            },
                            onClick = { viewModel.addChild() }
                        )
                    },
                    floatingActionButtonPosition = FabPosition.End,
                    contentColor = Color.Black,
                    containerColor = Orange100
                ) {

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

                            Box {
                                ElevatedCard(
                                    elevation = CardDefaults.cardElevation(
                                        defaultElevation = 8.dp,

                                        ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            if (viewModel.currentParent.value?.id == 1L) {
                                                Toast
                                                    .makeText(
                                                        this@MainActivity,
                                                        "Не получится. Это root-элемент",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                                return@clickable
                                            }
                                            expandedParent.value = true
                                        },
                                    shape = RoundedCornerShape(10.dp),

                                    ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    {
                                        Text(
                                            modifier = Modifier.padding(
                                                top = 24.dp,
                                                bottom = 24.dp
                                            ),
                                            text = parentNow.value.name,
                                            fontSize = 13.sp,
                                        )
                                    }
                                }
                                DropdownMenu(
                                    expanded = expandedParent.value,
                                    onDismissRequest = { expandedParent.value = false },
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            expandedParent.value = false
                                            viewModel.changeCurrentParent(
                                                parentNow.value.idParent
                                            )
                                        },
                                        text = {
                                            Text("Перейти к родителю")
                                        }
                                    )
                                    Divider()
                                    DropdownMenuItem(
                                        onClick = {
                                            expandedParent.value = false
                                            viewModel.deleteParent()
                                        },
                                        text = {
                                            Text("Удалить")
                                        }
                                    )
                                }
                            }

                            Text(
                                color = Orange100,
                                text = "Дети",
                                modifier = Modifier.padding(top = 32.dp)
                            )

                            DropdownMenu(
                                expanded = expandedChild.value,
                                onDismissRequest = { expandedChild.value = false },
                            ) {
                                DropdownMenuItem(
                                    onClick = {
                                        expandedChild.value = false
                                        viewModel.changeCurrentParent(currentChild.id)
                                    },
                                    text = {
                                        Text("Перейти к детям")
                                    }
                                )
                                Divider()
                                DropdownMenuItem(
                                    onClick = {
                                        expandedChild.value = false
                                        viewModel.deleteParentFromChildren(currentChild)
                                    },
                                    text = {
                                        Text("Удалить")
                                    }
                                )
                            }

                            LazyColumn() {
                                itemsIndexed(listChildren.value) { _, item ->
                                    ElevatedCard(
                                        elevation = CardDefaults.cardElevation(
                                            defaultElevation = 8.dp
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clickable {
                                                currentChild = item
                                                expandedChild.value = true
                                            },
                                        shape = RoundedCornerShape(10.dp),

                                        ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                modifier = Modifier.padding(
                                                    top = 16.dp,
                                                    bottom = 16.dp
                                                ),
                                                text = item.name,
                                                fontSize = 12.sp
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
    }
}



