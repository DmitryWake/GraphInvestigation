package test.tasks

import converter.FileInputConverter
import test.converter.FileType


fun main() {
    val inputConverter = FileInputConverter()
    println("Считываем граф")
    val graph = inputConverter.parseGraphFromFile(FileType.TEST_FILE)

    val nodes = graph.nodes.toList()

    val trianglesList = mutableListOf<Triple<Int, Int, Int>>()

    println("Начинаем обход")
    for (i in nodes.indices) {
        val a = nodes[i]
        for (j in i + 1 until nodes.size) {
            val b = nodes[j]
            if (graph.hasNotConnection(a, b)) continue
            for (k in j + 1 until nodes.size) {
                val c = nodes[k]
                if (graph.hasConnection(b, c) && graph.hasConnection(a, c)) {
                    trianglesList.add(Triple(a, b, c))
                    println("Нашли клику, теперь их: ${trianglesList.size}")
                }
            }
        }
    }

    println("Всего клик ${trianglesList.size}")

}