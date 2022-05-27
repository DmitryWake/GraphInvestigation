package test.tasks

import converter.FileInputConverter
import test.converter.FileType
import test.utilit.Clique
import test.utilit.GraphDegreeFinder

fun main() {
    val finder = GraphDegreeFinder(FileInputConverter().parseGraphFromFile(FileType.VK_GRAPH_FILE))
    println("${finder.maxDegree} ${finder.minDegree} ${finder.averageDegree}")
    print(finder.createProbabilityFunction())
}