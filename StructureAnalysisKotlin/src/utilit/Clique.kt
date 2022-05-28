package test.utilit

import model.GraphModel
import kotlin.collections.ArrayDeque

class Clique(private val graph: GraphModel) {

    var cliqueList: MutableSet<Set<Int>> = mutableSetOf()
    private val queue = ArrayDeque<Triple<MutableSet<Int>, MutableSet<Int>, MutableSet<Int>>>(graph.size)

    fun findCliques() {
        queue.add(Triple(graph.vertexes, mutableSetOf(), mutableSetOf()))

        while (queue.isNotEmpty()) {
            val data = queue.removeFirst()
            println("Новый вызов")
            findCliques(data.first, data.second, data.third)
        }
    }

    /*suspend fun parallelCliques() {
        stack.push(Triple(graph.vertexes, mutableSetOf(), mutableSetOf()))

        while (stack.isNotEmpty()) {
            if (stack.size > 2) {
                val data1 = stack.pop()
                val task1 = GlobalScope.async {
                    findCliques(data1.first, data1.second, data1.third)
                }
                val data2 = stack.pop()
                val task2 = GlobalScope.async {
                    findCliques(data2.first, data2.second, data2.third)
                }
                awaitAll(task1, task2)
            } else {
                val data = stack.pop()
                findCliques(data.first, data.second, data.third)
            }

        }
    }*/

    private fun findCliques(candidates: MutableSet<Int>, not: MutableSet<Int>, compsub: MutableSet<Int>) {
        if (compsub.size >= 3) return

        println("Оперируем с ${compsub.size + 1}")

        while (candidates.isNotEmpty()) {
            var condition = not.isNotEmpty()

            for (notV in not) {
                for (candidate in candidates) {
                    if (graph.hasNotConnection(notV, candidate)) {
                        condition = false
                        break
                    }
                }
                if (!condition) break
            }

            if (condition) break

            val v = candidates.first()
            val oldSize = compsub.size
            compsub.add(v)
            val newSize = compsub.size
            if (oldSize == newSize) {
                candidates.remove(v)
                compsub.remove(v)
                not.add(v)
                continue
            }

            val newCandidates = candidates.filter { graph.hasConnection(it, v) }
            val newNot = not.filter { graph.hasConnection(it, v) }

            if (newCandidates.isEmpty() && newNot.isEmpty()) {
                if (compsub.size == 3) {
                    cliqueList.add(compsub.toSet())
                    println("Нашли клику, теперь их ${cliqueList.size}")
                }
            } else if (compsub.size <= 3 && newCandidates.isNotEmpty()) {
                queue.addLast(Triple(newCandidates.toMutableSet(), newNot.toMutableSet(), compsub.toMutableSet()))
            }

            candidates.remove(v)
            compsub.remove(v)
            not.add(v)
        }

        println("Выход из вызова")
    }

}