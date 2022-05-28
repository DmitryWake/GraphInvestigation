package adapter

import org.jgrapht.graph.DefaultUndirectedWeightedGraph
import org.jgrapht.graph.DefaultWeightedEdge
import model.GraphModel

class JGraphAdapter(private val graph: GraphModel) {

    fun getJGraph(): DefaultUndirectedWeightedGraph<Int, DefaultWeightedEdge> {
        val jGraph = DefaultUndirectedWeightedGraph<Int, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)

        graph.vertexes.forEach {
            jGraph.addVertex(it)
        }

        graph.edges.forEach { (vertex, edges) ->
            edges.forEach { target ->
                val edge = jGraph.addEdge(vertex, target)

                edge?.let { jGraph.setEdgeWeight(it, 1.0) }
            }
        }

        return jGraph
    }
}