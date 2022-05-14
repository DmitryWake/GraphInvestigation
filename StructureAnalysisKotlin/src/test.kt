package test

import converter.FileInputConverter

fun main() {
    val converter = FileInputConverter()

    val graph = converter.parseGraphFromFile(FileInputConverter.FileType.WEB_GOOGLE_FILE)
    println(graph.size)
}