package test.model

import java.util.*

data class GraphModel(
    val nodes: SortedSet<Int>,
    val connections: Map<Int, List<Int>>
) {
    val size: Int
        get() = nodes.size

    fun hasConnection(a: Int, b: Int): Boolean =
        connections[a]?.contains(b) == true || connections[b]?.contains(a) == true

    fun hasNotConnection(a: Int, b: Int): Boolean = !hasConnection(a, b)
}