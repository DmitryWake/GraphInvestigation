package tasks

import adapter.JGraphAdapter
import converter.FileInputConverter
import org.jgrapht.GraphMetrics
import org.jgrapht.graph.DefaultUndirectedWeightedGraph
import org.jgrapht.graph.DefaultWeightedEdge
import test.utilit.GraphDistanceHelper

fun main() {
    // Инициализируем графы
    val graph =
        FileInputConverter().parseGraphFromAnyFile(
            "C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\socfb-Reed98.mtx",
            ' '
        )
    val adapter = JGraphAdapter(graph)
    val jGraph = adapter.getJGraph()

    // Начало расчёта
    var startTime = System.nanoTime()
    val helper = GraphDistanceHelper(graph)
    helper.init()
    var endTime = System.nanoTime()

    // Вывод
    println("Диаметр: ${helper.diameter}")
    println("Радиус: ${helper.radius}")
    println("90-перцентиль: ${helper.percentile90}")
    println("Время работы (ms): ${(endTime - startTime) / 1000000}")

    // Начало работы библиотечной реализации
    startTime = System.nanoTime()
    println("Диаметр (library): ${GraphMetrics.getDiameter(jGraph)}")
    println("Радиус (library): ${GraphMetrics.getRadius(jGraph)}")
    endTime = System.nanoTime()
    print("Время работы библиотечной (ms): ${(endTime - startTime) / 1000000}")
}