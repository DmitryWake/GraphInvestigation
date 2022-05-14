package converter

import model.GraphModel
import java.io.File
import java.util.*

class FileInputConverter {
    private val astroGraphFile: File by lazy {
        File(CA_ASTRO_PH_PATH)
    }
    private val vkGraphFile: File by lazy {
        File(VK_PATH)
    }
    private val webGoogleFile: File by lazy {
        File(WEB_GOOGLE_PATH)
    }

    fun parseGraphFromFile(fileType: FileType): MutableMap<Int, MutableList<Int>> {
        return when (fileType) {
            FileType.ASTRO_GRAPH_FILE -> parseGraphFromAstroFile()
            FileType.VK_GRAPH_FILE -> TODO()
            FileType.WEB_GOOGLE_FILE -> parseGraphFromGoogleFile()
        }
    }

    private fun parseGraphFromAstroFile(): MutableMap<Int, MutableList<Int>> {
        val resultMap = mutableMapOf<Int, MutableList<Int>>()

        astroGraphFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }

                if (resultMap.containsKey(data.first())) {
                    resultMap[data.first()]?.add(data.last())
                } else {
                    resultMap[data.first()] = mutableListOf(data.last())
                }

                if (resultMap.containsKey(data.last())) {
                    resultMap[data.last()]?.add(data.first())
                } else {
                    resultMap[data.last()] = mutableListOf(data.first())
                }
            }
        }

        return resultMap
    }

    private fun parseGraphFromGoogleFile(): MutableMap<Int, MutableList<Int>> {
        val resultMap = mutableMapOf<Int, MutableList<Int>>()

        webGoogleFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }

                if (resultMap.containsKey(data.first())) {
                    resultMap[data.first()]?.add(data.last())
                } else {
                    resultMap[data.first()] = mutableListOf(data.last())
                }

                if (resultMap.containsKey(data.last())) {
                    resultMap[data.last()]?.add(data.first())
                } else {
                    resultMap[data.last()] = mutableListOf(data.first())
                }
            }
        }

        return resultMap
    }

    fun createGraphFromFile(fileType: FileType): GraphModel {
        return when (fileType) {
            FileType.ASTRO_GRAPH_FILE -> createGraphFromAstroFile()
            FileType.VK_GRAPH_FILE -> TODO()
            FileType.WEB_GOOGLE_FILE -> createGraphFromGoogleFile()
        }
    }

    private fun createGraphFromAstroFile(): GraphModel {
        val nodes = mutableListOf<GraphModel.NodeModel>()

        astroGraphFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }

                val firstNode = nodes.find { it.id == data.first() }
                if (firstNode == null) {
                    nodes.add(GraphModel.NodeModel(id = data.first(), connections = mutableMapOf(data.last() to 1)))
                } else {
                    firstNode.connections[data.last()] = 1
                }

                val secondNode = nodes.find { it.id == data.last() }
                if (secondNode == null) {
                    nodes.add(GraphModel.NodeModel(id = data.last(), connections = mutableMapOf(data.first() to 1)))
                } else {
                    secondNode.connections[data.first()] = 1
                }
            }
        }

        return GraphModel(id = FileType.ASTRO_GRAPH_FILE.hashCode(), nodes = nodes)
    }

    private fun createGraphFromGoogleFile(): GraphModel {
        val nodes = mutableListOf<GraphModel.NodeModel>()

        webGoogleFile.forEachLine { line ->
            if (!line.startsWith('#')) {
                val data = line.split('\t').map { it.toInt() }

                val firstNode = nodes.find { it.id == data.first() }
                if (firstNode == null) {
                    nodes.add(GraphModel.NodeModel(id = data.first(), connections = mutableMapOf(data.last() to 1)))
                } else {
                    firstNode.connections[data.last()] = 1
                }

                val secondNode = nodes.find { it.id == data.last() }
                if (secondNode == null) {
                    nodes.add(GraphModel.NodeModel(id = data.last(), connections = mutableMapOf(data.first() to 1)))
                } else {
                    secondNode.connections[data.first()] = 1
                }
            }
        }

        return GraphModel(id = FileType.WEB_GOOGLE_FILE.hashCode(), nodes = nodes)
    }

    enum class FileType {
        ASTRO_GRAPH_FILE, VK_GRAPH_FILE, WEB_GOOGLE_FILE
    }
}