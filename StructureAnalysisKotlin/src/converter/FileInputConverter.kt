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
            FileType.TEST_FILE -> parseGraphFromTestFile()
        }
    }

    private fun parseGraphFromTestFile(): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()

        File(TEST_INPUT_PATH).forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split(' ').map { it.toInt() }
                nodes.add(data.first())
                nodes.add(data.last())

                if (connections.containsKey(data.first())) {
                    connections[data.first()]!!.add(data.last())
                } else {
                    connections[data.first()] = mutableListOf(data.last())
                }
            }
        }

        return GraphModel(nodes = nodes.toSortedSet(), connections = connections)
    }

    private fun parseGraphFromAstroFile(): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()

        astroGraphFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }
                nodes.add(data.first())
                nodes.add(data.last())

                if (connections.containsKey(data.first())) {
                    connections[data.first()]!!.add(data.last())
                } else {
                    connections[data.first()] = mutableListOf(data.last())
                }
            }
        }

        return GraphModel(nodes = nodes.toSortedSet(), connections = connections)
    }

    private fun parseGraphFromGoogleFile(): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()

        webGoogleFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }
                nodes.add(data.first())
                nodes.add(data.last())

                if (connections.containsKey(data.first())) {
                    connections[data.first()]!!.add(data.last())
                } else {
                    connections[data.first()] = mutableListOf(data.last())
                }
            }
        }

        return GraphModel(nodes = nodes.toSortedSet(), connections = connections)
    }

    private fun parseGraphFromVkFile(): GraphModel {
        val connections = mutableMapOf<Int, MutableList<Int>>()
        val nodes = mutableSetOf<Int>()

        vkGraphFile.forEachLine { line ->
            if (!line.startsWith('u')) {
                val data = line.split(',').map { it.toInt() }.subList(0, 2)

                nodes.add(data.first())
                nodes.add(data.last())

                if (connections.containsKey(data.first())) {
                    connections[data.first()]!!.add(data.last())
                } else {
                    connections[data.first()] = mutableListOf(data.last())
                }
            }
        }

        return GraphModel(nodes = nodes.toSortedSet(), connections = connections)
    }
}