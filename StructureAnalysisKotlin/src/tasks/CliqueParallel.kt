package tasks

import adapter.JGraphAdapter
import converter.FileInputConverter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import org.jgrapht.GraphMetrics
import converter.FileType
import java.util.ArrayList

suspend fun main() {
    val graph = FileInputConverter().parseGraphFromAnyFile(
        "C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\socfb-Middlebury45.mtx",
        ' '
    )
    val cliques = mutableSetOf<Set<Int>>()

    // Начало процесса
    var startTime = System.nanoTime()
    graph.vertexes.forEach { a ->
        val neighbors = graph.edges[a]
        neighbors?.forEach { b->
            val neighborsB = graph.edges[b]
            neighborsB?.forEach { c ->
                if (graph.hasConnection(a, c)) {
                    cliques.add(setOf(a, b, c))
                }
            }
        }
    }
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