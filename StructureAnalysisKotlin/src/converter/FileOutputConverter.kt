package test.converter

import converter.*
import model.GraphModel
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
        }

        println(currentFile.canWrite())
        val dataList = mutableListOf<String>()

        graph.edges.keys.forEach { key ->
            graph.edges[key]!!.forEach { value ->
                val dataString = "$key $value"
                dataList.add(dataString)
            }
        }

        val resultString = dataList.joinToString("\n")

        print(resultString.length)
        currentFile.writeText(resultString)
    }
}