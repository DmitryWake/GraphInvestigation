const Deque = require("collections/deque");
const selectLandmarks = require("./select-landmarks.js");

exports.distances = function (graph, start) { //BFS
    let q = new Deque([start]);

    graph[start].distances[start] = 0;
    graph[start].marked = true;

    while (q.length) {
        let v = q.pop()

        for (let i of graph[v].linked) {
            if (!graph[i].marked) {

                graph[i].distances[start] = graph[v].distances[start] + 1;
                graph[i].marked = true;
                q.unshift(i); 
            }
        }
    }

    for (let v in graph) {
        graph[v].marked = false;
    }
}

exports.precompute = function (graph, U = [], method = "random", numLandmarks = 20, M = 500) {

    if (!U.length) {
        if (method == "random") {
            U = selectLandmarks.randomLandmarks(graph, numLandmarks);
        } else if (method == "highest-degree") {
            U = selectLandmarks.highestDegree(graph, numLandmarks);
        } else if (method == "best-coverage") {
            U = selectLandmarks.bestCoverage(graph, numLandmarks, M);
        } else {
            return "Укажите допустимый метод выбора ориентиров";
        }
    }

    for (let u of U) {
        exports.distances(graph, u);
    }

    return U;
}

exports.landmarksBasic = function (graph, s, t, U = [], numLandmarks = 20, method = "random") {
    if (!graph.hasOwnProperty(s) || !graph.hasOwnProperty(t)) {
        return -1;
    } 

    if (s == t) return 0;
    
    if (!U.length) {
        U = exports.precompute(graph, U, method, numLandmarks);
    }

    let d = Infinity;

    for (let u of U) {
        if (graph[s].distances[u] && graph[t].distances[u]) {
            if (d > graph[s].distances[u] + graph[t].distances[u]) {
                d = graph[s].distances[u] + graph[t].distances[u];
            }
        } else if (graph[s].distances[u] == 0 && graph[t].distances[u]) {
            return graph[t].distances[u];
        } else if (graph[s].distances[u] && graph[t].distances[u] == 0){
            return graph[s].distances[u];
        }
    }

    if (d == Infinity) {
        return -1;
    }

    return d;
}
