package model

data class GraphModel(
    val id: Int,
    val nodes: MutableList<NodeModel>
) {

    val size: Int
        get() = nodes.size

    data class NodeModel(
        val id: Int,
        val connections: MutableMap<Int, Int>
    ) {
        val degree: Int
            get() = connections.size
    }
}
