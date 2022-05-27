package test

import jetbrains.datalore.plot.server.config.transform.bistro.util.coord
import jetbrains.letsPlot.coordFixed
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomBar
import jetbrains.letsPlot.geom.geomDensity
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.ggsize
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.scale.*
import java.io.File

fun main() {
    val fileVk = File("C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\VKFunc.txt")
    val fileAstro = File("C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\AstroFunc.txt")
    val fileGoogle = File("C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\GoogleFunc.txt")

    val vkData = createData(fileVk)
    val astroData = createData(fileAstro)
    val googleData = createData(fileGoogle)

    val fig = letsPlot(vkData) + geomLine(color = "dark_green") {
        x = "vertexDegree"
        y = "probability"
    } + scaleXLog10() + scaleYLog10()

    ggsave(fig, "vkFuncLog.png", path = "C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\")
}

fun createData(file: File): Map<String, Any> {
    val vertex = mutableListOf<Int>()
    val y = mutableListOf<Double>()

    file.forEachLine { line ->
        line.split(' ').let {
            vertex.add(it.first().toInt())
            y.add(it.last().toDouble())
        }
    }

    return mapOf(
        "vertexDegree" to vertex,
        "probability" to y
    )
}