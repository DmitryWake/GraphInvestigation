package test.tasks

import adapter.JGraphAdapter
import converter.FileInputConverter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.jgrapht.GraphMetrics
import converter.FileType
import java.util.ArrayList




private const val COROUTINES_COUNT = 1

suspend fun main() {
    val graph = FileInputConverter().parseGraphFromFile(FileType.ASTRO_GRAPH_FILE)

    val cliques = mutableSetOf<Set<Int>>()

    // Начало процесса
    var startTime = System.nanoTime()
    val nodes = graph.vertexes.toList()
    val chunk = nodes.size / COROUTINES_COUNT
    val tasks = mutableListOf<Deferred<Unit>>()
    val lists = chunkify(nodes, chunk)

    lists.forEach { sublist ->
        val task = GlobalScope.async {
            sublist.forEach { a ->
                val neighbors = graph.edges[a]
                neighbors?.forEach { b->
                    val neighborsB = graph.edges[b]
                    neighborsB?.forEach { c ->
                        if (neighbors.contains(c)) {
                            cliques.add(setOf(a, b, c))
                        }
                    }
                }
            }
        }

        tasks.add(task)
    }

    tasks.awaitAll()
    var endTime = System.nanoTime()

    println("Всего клик ${cliques.size}")
    println("Время работы: ${(endTime - startTime) / 1000000}")

    val jGraph = JGraphAdapter(graph).getJGraph()
    startTime = System.nanoTime()
    val count = GraphMetrics.getNumberOfTriangles(jGraph)
    println("Результат JGraphT: $count")
    endTime = System.nanoTime()
    println("Время JGraphT: ${(endTime - startTime) / 1000000}")
}

private fun <T> chunkify(list: List<T>, chunkSize: Int): List<List<T>> {
    val chunks: MutableList<List<T>> = ArrayList()
    var i = 0
    while (i < list.size) {
        val chunk: List<T> = ArrayList(list.subList(i, list.size.coerceAtMost(i + chunkSize)))
        chunks.add(chunk)
        i += chunkSize
    }
    return chunks
}