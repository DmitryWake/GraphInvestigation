package test.tasks

import converter.FileInputConverter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import test.converter.FileType

suspend fun main() {
    val inputConverter = FileInputConverter()
    println("Считываем граф")
    val graph = inputConverter.parseGraphFromFile(FileType.ASTRO_GRAPH_FILE)

    val nodes = graph.vertexes.toList()

    println("Начинаем обход")

    val firstTask = GlobalScope.async {
        var trianglesCount = 0

        for (i in 0 until nodes.size / 4) {
            val a = nodes[i]
            for (j in i + 1 until nodes.size) {
                val b = nodes[j]
                if (graph.hasNotConnection(a, b)) continue
                for (k in j + 1 until nodes.size) {
                    val c = nodes[k]
                    if (graph.hasConnection(b, c) && graph.hasConnection(a, c)) {
                        ++trianglesCount
                    }
                }
            }
        }

        trianglesCount
    }

    val secondTask = GlobalScope.async {
        var trianglesCount = 0

        for (i in nodes.size / 4 until nodes.size / 2) {
            val a = nodes[i]
            for (j in i + 1 until nodes.size) {
                val b = nodes[j]
                if (graph.hasNotConnection(a, b)) continue
                for (k in j + 1 until nodes.size) {
                    val c = nodes[k]
                    if (graph.hasConnection(b, c) && graph.hasConnection(a, c)) {
                        ++trianglesCount
                    }
                }
            }
        }
        trianglesCount
    }

    val thirdTask = GlobalScope.async {
        var trianglesCount = 0

        for (i in nodes.size / 2 until nodes.size / 4 * 3) {
            val a = nodes[i]
            for (j in i + 1 until nodes.size) {
                val b = nodes[j]
                if (graph.hasNotConnection(a, b)) continue
                for (k in j + 1 until nodes.size) {
                    val c = nodes[k]
                    if (graph.hasConnection(b, c) && graph.hasConnection(a, c)) {
                        ++trianglesCount
                    }
                }
            }
        }

        trianglesCount
    }

    val fourTask = GlobalScope.async {
        var trianglesCount = 0

        for (i in nodes.size / 4 * 3 until nodes.size) {
            val a = nodes[i]
            for (j in i + 1 until nodes.size) {
                val b = nodes[j]
                if (graph.hasNotConnection(a, b)) continue
                for (k in j + 1 until nodes.size) {
                    val c = nodes[k]
                    if (graph.hasConnection(b, c) && graph.hasConnection(a, c)) {
                        ++trianglesCount
                    }
                }
            }
        }

        trianglesCount
    }

    val result = awaitAll(firstTask, secondTask, thirdTask, fourTask)

    println("Всего клик ${result.sum()}")

}