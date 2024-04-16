package com.sd.tree_compose.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sd.tree_compose.dto.NodeItem

@Entity
data class NodeItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String = "",
    val idParent: Long,
    val parents: String = "0"
) {
    fun toDto() = NodeItem(
        id = id,
        name = name,
        idParent = idParent,
        parents = stringToList(parents)
    )

    companion object {
        fun fromDto(dto: NodeItem) =
            NodeItemEntity(
                id = dto.id,
                name = dto.name,
                idParent = dto.idParent,
                parents = listToString(dto.parents)
            )
    }

}

fun listToString(list: List<Long>) = list.joinToString(",")

fun stringToList(str: String) = str
    .split(",")
    .toList()
    .map {
        it.toLong()
    }

fun List<NodeItemEntity>.toDto(): List<NodeItem> = map(NodeItemEntity::toDto)