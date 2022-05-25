package tasks

import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomDensity
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.letsPlot
import java.io.File

fun main() {
    val fileVk = File("C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\VK B2.txt")
    val fileAstro = File("C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\Astro B2.txt")
    val fileGoogle = File("C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\Google B2.txt")

    val vkData = createData(fileVk)
    val astroData = createData(fileAstro)
    val googleData = createData(fileGoogle)

    val fig = letsPlot(googleData) + geomLine(color = "dark_green") {
        x = "x% of vertexes"
        y = "the proportion of vertices in the largest component"
    }

    ggsave(fig, "googleB2.png", path = "C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\")
}

fun createData(file: File): Map<String, Any> {
    val vertex = mutableListOf<Double>()
    val y = mutableListOf<Double>()

    file.forEachLine { line ->
        line.split(' ').let {
            vertex.add(it.first().toDouble())
            y.add(it.last().toDouble())
        }
    }

    return mapOf(
        "x% of vertexes" to vertex,
        "the proportion of vertices in the largest component" to y
    )
}