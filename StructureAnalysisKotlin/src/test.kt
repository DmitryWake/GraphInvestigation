package test

import converter.FileInputConverter
import test.converter.FileOutputConverter
import test.converter.FileType

fun main() {
    val inputConverter = FileInputConverter()
    val outputConverter = FileOutputConverter()

    val graph = inputConverter.parseGraphFromFile(FileType.VK_GRAPH_FILE)
    println(graph.size)
    outputConverter.parseToInputFile(graph, FileType.VK_GRAPH_FILE)
}