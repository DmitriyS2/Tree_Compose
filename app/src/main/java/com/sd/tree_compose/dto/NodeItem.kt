package com.sd.tree_compose.dto

data class NodeItem(
    val id: Long = 0L,
    val name: String = "",
    val idParent: Long = 0L,
    val parents: List<Long> = listOf(0L)
)