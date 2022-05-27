package converter

import test.converter.FileType
import test.model.GraphModel
import java.io.File

class FileInputConverter {
    private val astroGraphFile: File by lazy {
        File(CA_ASTRO_PH_INPUT_PATH)
    }
    private val vkGraphFile: File by lazy {
        File(VK_INPUT_PATH)
    }
    private val webGoogleFile: File by lazy {
        File(WEB_GOOGLE_INPUT_PATH)
    }

    fun parseGraphFromFile(fileType: FileType): GraphModel {
        return when (fileType) {
            FileType.ASTRO_GRAPH_FILE -> parseGraphFromAstroFile()
            FileType.VK_GRAPH_FILE -> parseGraphFromVkFile()
            FileType.WEB_GOOGLE_FILE -> parseGraphFromGoogleFile()
        }
    }

    fun parseGraphFromAnyFile(path: String, delimiter: Char): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()
        val degrees = mutableMapOf<Int, Int>()

        File(path).forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split(delimiter).map { it.toInt() }
                if (data.first() == data.last()) {
                    nodes.add(data.first())
                    if (degrees[data.first()] == null) {
                        degrees[data.first()] = 1
                    } else {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    }
                } else {
                    nodes.add(data.first())
                    nodes.add(data.last())

                    if (degrees.containsKey(data.first())) {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    } else {
                        degrees[data.first()] = 1
                    }
                    if (degrees.containsKey(data.last())) {
                        degrees[data.last()] = degrees[data.last()]!! + 1
                    } else {
                        degrees[data.last()] = 1
                    }

                    if (connections.containsKey(data.first())) {
                        connections[data.first()]!!.add(data.last())
                    } else {
                        connections[data.first()] = mutableListOf(data.last())
                    }
                }
            }
        }


        return GraphModel(vertexes = nodes.toSortedSet(), edges = connections, degrees = degrees)
    }

    private fun parseGraphFromAstroFile(): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()
        val degrees = mutableMapOf<Int, Int>()

        astroGraphFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }
                if (data.first() == data.last()) {
                    nodes.add(data.first())
                    if (degrees[data.first()] == null) {
                        degrees[data.first()] = 1
                    } else {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    }
                } else {
                    nodes.add(data.first())
                    nodes.add(data.last())

                    if (degrees.containsKey(data.first())) {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    } else {
                        degrees[data.first()] = 1
                    }
                    if (degrees.containsKey(data.last())) {
                        degrees[data.last()] = degrees[data.last()]!! + 1
                    } else {
                        degrees[data.last()] = 1
                    }

                    if (connections.containsKey(data.first())) {
                        connections[data.first()]!!.add(data.last())
                    } else {
                        connections[data.first()] = mutableListOf(data.last())
                    }
                }
            }
        }


        return GraphModel(vertexes = nodes.toSortedSet(), edges = connections, degrees = degrees)
    }

    private fun parseGraphFromGoogleFile(): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()
        val degrees = mutableMapOf<Int, Int>()

        webGoogleFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }
                if (data.first() == data.last()) {
                    nodes.add(data.first())
                    if (degrees[data.first()] == null) {
                        degrees[data.first()] = 1
                    } else {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    }
                } else {
                    nodes.add(data.first())
                    nodes.add(data.last())

                    if (degrees.containsKey(data.first())) {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    } else {
                        degrees[data.first()] = 1
                    }
                    if (degrees.containsKey(data.last())) {
                        degrees[data.last()] = degrees[data.last()]!! + 1
                    } else {
                        degrees[data.last()] = 1
                    }

                    if (connections.containsKey(data.first())) {
                        connections[data.first()]!!.add(data.last())
                    } else {
                        connections[data.first()] = mutableListOf(data.last())
                    }
                }
            }
        }


        return GraphModel(vertexes = nodes.toSortedSet(), edges = connections, degrees = degrees)
    }

    private fun parseGraphFromVkFile(): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()
        val degrees = mutableMapOf<Int, Int>()

        vkGraphFile.forEachLine { line ->
            if (!line.startsWith('u')) {
                val data = line.split(',').map { it.toInt() }.subList(0, 2)

                if (data.first() == data.last()) {
                    nodes.add(data.first())
                    if (degrees[data.first()] == null) {
                        degrees[data.first()] = 1
                    } else {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    }
                } else {
                    nodes.add(data.first())
                    nodes.add(data.last())

                    if (degrees.containsKey(data.first())) {
                        degrees[data.first()] = degrees[data.first()]!! + 1
                    } else {
                        degrees[data.first()] = 1
                    }
                    if (degrees.containsKey(data.last())) {
                        degrees[data.last()] = degrees[data.last()]!! + 1
                    } else {
                        degrees[data.last()] = 1
                    }

                    if (connections.containsKey(data.first())) {
                        connections[data.first()]!!.add(data.last())
                    } else {
                        connections[data.first()] = mutableListOf(data.last())
                    }
                }
            }
        }

        return GraphModel(vertexes = nodes.toSortedSet(), edges = connections, degrees = degrees)
    }

    private fun checkIfExists(connections: Map<Int, MutableList<Int>>, pair: Pair<Int, Int>): Boolean {
        return connections[pair.first]?.contains(pair.second) == true ||
                connections[pair.second]?.contains(pair.first) == true
    }
}