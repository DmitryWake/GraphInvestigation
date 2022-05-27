package test.model

import java.util.*

data class GraphModel(
    val vertexes: SortedSet<Int>,
    val edges: Map<Int, List<Int>>,
    val degrees: Map<Int, Int>
) {
    val size: Int
        get() = vertexes.size

    fun hasConnection(a: Int, b: Int): Boolean =
        edges[a]?.contains(b) == true || edges[b]?.contains(a) == true

    fun hasNotConnection(a: Int, b: Int): Boolean = !hasConnection(a, b)

    fun vertexDegree(vertex: Int): Int = degrees[vertex] ?: 0
}