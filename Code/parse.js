const fs = require("fs");

exports.parseFromTxt = function (filename, oriented = true, separator = /[ ,\t]/) {
    let data = fs.readFileSync(filename, 'utf-8');
    data = data.split('\n');

    data = data.filter(u => u != "");

    let graph = {};

    for (let i = 0; i < data.length; i++) {
        
        let [parent, child] = data[i].split(separator);
        parent = +parent;
        child = +child;

        if (parent in graph) {
            if (!(child in graph[parent].linked)) {
                graph[parent].linked.add(child);
            }
        } else {
            graph[parent] = {
                linked: new Set([child]),
                distances: {},
                marked: false
            }
        }

        if (oriented) {
            if (!(child in graph)) {
                graph[child] = {
                    linked: new Set(),
                    distances: {},
                    marked: false
                }
            }
        } else {
            if (child in graph) {
                if (!(parent in graph[child].linked)) {
                    graph[child].linked.add(parent);
                }
            } else {
                graph[child] = {
                    linked: new Set([parent]),
                    distances: {},
                    marked: false
                }
            }
        }
    }

    for (let v in graph) {
        graph[v].linked = Array.from(graph[v].linked);
    }

    return graph;
}