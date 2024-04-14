package com.sd.tree_compose.repository

import androidx.lifecycle.LiveData
import com.sd.tree_compose.dto.NodeItem

interface Repository {

    fun getAllChildren(id: Long): List<NodeItem>

    fun getItemById(id: Long): NodeItem?

    fun getMaxId(): LiveData<Long>

    fun add(nodeItem: NodeItem): Long

    fun getIdCurrentParent(): Long

    fun setParentToSharedpref(id: Long)

    fun delete(id: Long)

    fun deleteItemByIdParent(idParent: Long)

    fun getAll(): MutableList<NodeItem>
}