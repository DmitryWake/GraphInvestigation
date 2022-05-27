package tasks

import converter.FileInputConverter
import test.utilit.Clique

fun main() {
    println("Считываем граф")
    val graph =
        FileInputConverter().parseGraphFromAnyFile(
            "C:\\Users\\nikol\\IdeaProjects\\TestGraphs\\DataSets\\CA-GrQc.txt",
            '\t'
        )
    val helper = Clique(graph)
    helper.findCliques()
    println(helper.cliqueList.size)
}