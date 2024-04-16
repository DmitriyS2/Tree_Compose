package com.sd.tree_compose.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sd.tree_compose.activity.compose.ShowCardItem
import com.sd.tree_compose.activity.compose.ShowDropDownMenu
import com.sd.tree_compose.activity.compose.ShowListItem
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
                val currentChild = remember {
                    mutableStateOf(NodeItem())
                }
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
                                ShowCardItem(
                                    child = false,
                                    padding = 24,
                                    size = 13,
                                    currentChildOrParent = parentNow,
                                    expanded = expandedParent,
                                    context = this@MainActivity
                                )

                                ShowDropDownMenu(expanded = expandedParent,
                                    onChangeParent = {
                                        viewModel.changeCurrentParent(
                                            parentNow.value.idParent
                                        )
                                    },
                                    text = "Перейти к родителю",
                                    onDeleteParent = {
                                        viewModel.deleteParent()
                                    })
                            }

                            Box {
                                Text(
                                    color = Orange100,
                                    text = "Дети",
                                    modifier = Modifier.padding(top = 32.dp)
                                )

                                ShowDropDownMenu(expanded = expandedChild,
                                    onChangeParent = {
                                        viewModel.changeCurrentParent(currentChild.value.id)
                                    },
                                    text = "Перейти к детям",
                                    onDeleteParent = {
                                        viewModel.deleteParentFromChildren(currentChild.value)
                                    })
                            }

                            ShowListItem(
                                listChildren = listChildren,
                                currentChild = currentChild,
                                expandedChild = expandedChild
                            )
                        }
                    }
                }
            }
        }
    }
}



