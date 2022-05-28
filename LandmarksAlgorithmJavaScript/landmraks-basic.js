const Deque = require("collections/deque");
const selectLandmarks = require("./select-landmarks.js");
const jsonfile = require('jsonfile');

//Запускаем BFS из вершины, записываем расстояние каждой достигшей вершине от исходной.
exports.distances = function (graph, start) {
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

// Поиск ориентиров и расстояния до них от каждой вершины. Записывает файл с последними найдеными ориентирами и временем работы. 
// Возвращает ориентиры.
exports.precompute = function (graph, U = [], method = "random", numLandmarks = 20, M = 500) { 
    let time = Date.now();

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

    jsonfile.writeFileSync(`../JSON/landmarks-${Date.now()}.json`, {
        landmarks: U,
        workingTime: Date.now() - time,
        numberOfLandmarks: numLandmarks,
        method
    }, { spaces: 2, EOL: '\r\n' });

    return U;
}

//Алгоритм landmark-basic. Можно передать уже готовый список ориентиров, тогда они считаться заново не будут.
//Возвращает оценку расстояния и время работы(во время работы поиск ориентиров не включено).
exports.landmarksBasic = function (graph, s, t, numLandmarks = 20, method = "random", U = [], M = 500) {
    if (!graph.hasOwnProperty(s) || !graph.hasOwnProperty(t)) {
        return {
            distance: -1,
            workingTime: 0
        };
    } 

    if (s == t) {
        return {
            distance: 0,
            workingTime: 0
        };
    }   

    if (!U.length) {
        U = exports.precompute(graph, U, method, numLandmarks, M);
    }

    let time = Date.now();

    let d = Infinity;

    for (let u of U) {
        if (graph[s].distances[u] && graph[t].distances[u]) {
            if (d > graph[s].distances[u] + graph[t].distances[u]) {
                d = graph[s].distances[u] + graph[t].distances[u];
            }
        } else if (graph[s].distances[u] == 0 && graph[t].distances[u]) {
            return {
                distance: graph[t].distances[u],
                workingTime: Date.now() - time
            };
        } else if (graph[s].distances[u] && graph[t].distances[u] == 0){
            return {
                distance: graph[s].distances[u],
                workingTime: Date.now() - time
            };
        }
    }

    if (d == Infinity) {
        return {
            distance: -1,
            workingTime: Date.now() - time
        };
    }

    return {
        distance: d,
        workingTime: Date.now() - time
    };
}
