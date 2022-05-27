package test.tasks

import converter.FileInputConverter
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import test.converter.FileType
import java.util.ArrayList




private const val COROUTINES_COUNT = 80

suspend fun main() {
    val startTime = System.nanoTime()
    println("Считываем граф")
    val graph =
        FileInputConverter().parseGraphFromFile(FileType.WEB_GOOGLE_FILE)

    val nodes = graph.vertexes.toList()
    val chunk = nodes.size / COROUTINES_COUNT
    val tasks = mutableListOf<Deferred<Int>>()
    val lists = chunkify(nodes, chunk)
    lists.forEachIndexed { index, sublist ->

        val task = GlobalScope.async {
            var trianglesCount = 0

            for (i in sublist.indices) {
                val a = sublist[i]
                val start = nodes.indexOf(a)
                for (j in start + 1 until nodes.size) {
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

        tasks.add(task)
    }

    println("Начинаем обход")

    val result = tasks.awaitAll()
    val endTime = System.nanoTime()

    println("Всего клик ${result.sum()}")
    print((endTime - startTime) / 1000000000)

}

fun <T> chunkify(list: List<T>, chunkSize: Int): List<List<T>> {
    val chunks: MutableList<List<T>> = ArrayList()
    var i = 0
    while (i < list.size) {
        val chunk: List<T> = ArrayList(list.subList(i, list.size.coerceAtMost(i + chunkSize)))
        chunks.add(chunk)
        i += chunkSize
    }
    return chunks
}