package tasks

import converter.FileInputConverter
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.letsPlot
import converter.FileType
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.scale.scaleXLog10
import jetbrains.letsPlot.scale.scaleYLog10
import test.utilit.GraphDegreeFinder

fun main() {
    val graph = FileInputConverter().parseGraphFromFile(FileType.VK_GRAPH_FILE)
    // Поиск степеней
    val startTime = System.nanoTime()

    val finder = GraphDegreeFinder(graph)
    println("Максимальная степень: ${finder.maxDegree}")
    println("Минимальная степень: ${finder.minDegree}")
    println("Средняя степень: ${finder.averageDegree}")

    // Построение функции вероятности
    val function = finder.createProbabilityFunction()

    val endTime = System.nanoTime()

    // Отрисовка функции вероятности
    val data = createData(function)

    val fig = letsPlot(data){
        x = "vertexDegree"
        y = "probability"
    } + geomLine(color = "dark_green")   + scaleXLog10() + scaleYLog10()

    println("Время работы ms: ${(endTime - startTime) / 1000000}")

    ggsave(fig, "vkFuncLog.png", path = "C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\")
}

fun createData(data: Map<Int, Double>): Map<String, Any> {
    val vertex = data.keys.toList()
    val y = data.values.toList()

    return mapOf(
        "vertexDegree" to vertex,
        "probability" to y
    )
}