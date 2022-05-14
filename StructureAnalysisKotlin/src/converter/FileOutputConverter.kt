package test.converter

import converter.*
import test.model.GraphModel
import java.io.File

class FileOutputConverter {
    private val astroGraphFile: File by lazy {
        File(CA_ASTRO_PH_OUTPUT_PATH)
    }
    private val vkGraphFile: File by lazy {
        File(VK_OUTPUT_PATH)
    }
    private val webGoogleFile: File by lazy {
        File(WEB_GOOGLE_OUTPUT_PATH)
    }

    fun parseToInputFile(graph: GraphModel, fileType: FileType) {
        val currentFile = when (fileType) {
            FileType.ASTRO_GRAPH_FILE -> astroGraphFile
            FileType.VK_GRAPH_FILE -> vkGraphFile
            FileType.WEB_GOOGLE_FILE -> webGoogleFile
            FileType.TEST_FILE -> File(TEST_OUTPUT_PATH)
        }

        println(currentFile.canWrite())
        val dataList = mutableListOf<String>()

        graph.connections.keys.forEach { key ->
            graph.connections[key]!!.forEach { value ->
                val dataString = "$key $value"
                dataList.add(dataString)
            }
        }

        val resultString = dataList.joinToString("\n")

        print(resultString.length)
        currentFile.writeText(resultString)
    }
}