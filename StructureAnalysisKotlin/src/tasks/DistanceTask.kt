package test.tasks

import converter.FileInputConverter
import org.jgrapht.graph.DefaultUndirectedWeightedGraph
import org.jgrapht.graph.DefaultWeightedEdge
import test.utilit.GraphDistanceHelper

fun main() {
    val graph =
        FileInputConverter().parseGraphFromAnyFile(
            "C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\CA-GrQc.txt",
            '\t'
        )
    val jGraph = DefaultUndirectedWeightedGraph<Int, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)
    graph.vertexes.forEach {
        jGraph.addVertex(it)
    }

    graph.edges.forEach { vertext, edges ->
        edges.forEach { target ->
            val edge = jGraph.addEdge(vertext, target)

            edge?.let { jGraph.setEdgeWeight(it, 1.0) }
        }
    }

    val helper = GraphDistanceHelper(graph)
    helper.init()
    println(helper.diameter)
    println(helper.radius)
    println(helper.percentile90)
    println(helper.distanceMap)
}