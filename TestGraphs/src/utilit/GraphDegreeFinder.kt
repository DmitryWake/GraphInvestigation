package test.utilit

import test.model.GraphModel
import java.util.*

class GraphDegreeFinder(private val graphModel: GraphModel) {

    var maxDegree: Int = 0
        private set

    var minDegree: Int = Int.MAX_VALUE
        private set

    val averageDegree: Double

    init {
        var degreesCount = 0
        graphModel.vertexes.forEach { vertex ->
            val degree = graphModel.vertexDegree(vertex)
            if (degree > maxDegree) maxDegree = degree
            if (degree < minDegree) minDegree = degree

            degreesCount += degree
        }

        averageDegree = degreesCount.toDouble() / graphModel.size
    }

    fun createProbabilityFunction(): SortedMap<Int, Double> {

        val degreeGroup = graphModel.degrees.values.groupBy {
            it
        }.toSortedMap()

        val result = degreeGroup.mapValues {
            it.value.size.toDouble() / graphModel.size
        }

        return result.toSortedMap()
    }

}