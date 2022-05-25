package tasks

import org.jgrapht.graph.DefaultDirectedWeightedGraph
import org.jgrapht.graph.DefaultWeightedEdge
import java.io.File
import com.mxgraph.*
import com.mxgraph.layout.mxCircleLayout
import com.mxgraph.swing.mxGraphComponent
import org.jgrapht.ext.JGraphXAdapter
import javax.swing.JFrame

fun main() {
    val file = File("C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\metaGraph.txt")

    val lines = file.readLines()
    val n = lines.first().toInt()
    val dict = mutableMapOf<Int, String>()
    lines.subList(1, n + 1).forEachIndexed { index, line ->
        val data = line.split(' ').filter { it.isNotBlank() }.map { it.toInt() }
        if (data.size > 1) {
            dict[index] = "[${data.joinToString(",")}]"
        } else {
            dict[index] = data.first().toString()
        }
    }

    val jGraph = DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)

    dict.values.forEach {
        jGraph.addVertex(it)
    }

    lines.subList(n + 1, lines.size).forEach { line ->
        val data = line.split(' ').filter { it.isNotBlank() }.map { it.toInt() }
        val v1 = dict[data.first()]
        val v2 = dict[data.last()]

        val edge = jGraph.addEdge(v1, v2)
        if (edge != null) jGraph.setEdgeWeight(edge, 1.0)
    }

    val testGraph = DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge::class.java).apply {
        addVertex("1")
        addVertex("2")
        addVertex("Тоха")
        addEdge("1", "2")
        addEdge("1", "Тоха")
    }

    val adapter = JGraphXAdapter(testGraph)
    val frame = JFrame()
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    val layout = mxCircleLayout(adapter)
    layout.execute(adapter.defaultParent)

    frame.add(mxGraphComponent(adapter))

    frame.pack()
    frame.isLocationByPlatform = true
    frame.isVisible = true
}