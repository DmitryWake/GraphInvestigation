package test.utilit

import org.jgrapht.alg.shortestpath.DeltaSteppingShortestPath
import org.jgrapht.graph.DefaultUndirectedWeightedGraph
import org.jgrapht.graph.DefaultWeightedEdge
import model.GraphModel
import java.util.*
import kotlin.math.roundToInt

class GraphDistanceHelper(graph: GraphModel) {

    val distanceMap = mutableMapOf<Int, Pair<Int, Int>>()

    val radius by lazy {
        var min = Int.MAX_VALUE
        distanceMap.values.forEach {
            if (it.second < min && it.second != 0) min = it.second
        }
        min
    }
    val diameter by lazy {
        var max = 0
        distanceMap.values.forEach {
            if (it.second > max) max = it.second
        }
        max
    }
    val percentile90 by lazy {
        val distanceGroup = mutableMapOf<Int, Int>()
        distanceMap.values.forEach {
            if (distanceGroup.containsKey(it.second)) {
                distanceGroup[it.second] = distanceGroup[it.second]!! + 1
            } else {
                distanceGroup[it.second] = 1
            }
        }
        var group90 = (distanceGroup.values.sum() * 0.9).roundToInt()
        var currentDistance = 0
        val iterator = distanceGroup.toSortedMap().iterator()
        while (group90 > 0) {
            val it = iterator.next()
            group90 -= it.value
            currentDistance = it.key
        }
        currentDistance
    }
    private val vertexes: MutableSet<Int> = mutableSetOf()

    private val random = Random(System.currentTimeMillis())

    private val jGraph = DefaultUndirectedWeightedGraph<Int, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)

    private val helper = DeltaSteppingShortestPath(jGraph)

    init {
        graph.vertexes.forEach {
            jGraph.addVertex(it)
        }

        graph.edges.forEach { (vertex, edges) ->
            edges.forEach { target ->
                val edge = jGraph.addEdge(vertex, target)

                edge?.let { jGraph.setEdgeWeight(it, 1.0) }
            }
        }

        var iterator = graph.vertexes.iterator()
        while (vertexes.size < 500) {
            if (iterator.hasNext()) {
                val v = iterator.next()
                if (random.nextInt() % 4 == 3) vertexes.add(v)
            } else {
                iterator = graph.vertexes.iterator()
            }
        }

    }

    fun init() {
        val iterator = vertexes.iterator()

        while (iterator.hasNext()) {
            val v1 = iterator.next()
            val v2 = iterator.next()
            val distance = helper.getPathWeight(v1, v2).toInt().let {
                if (it == Int.MAX_VALUE) 0 else it
            }
            distanceMap[v1] = v2 to distance
            println("Вычислили расстояние ${distanceMap[v1]}")
        }
    }
}