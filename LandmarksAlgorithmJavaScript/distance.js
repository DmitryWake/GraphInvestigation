const Deque = require("collections/deque");

exports.bfs = function (graph, start, end, reset = true) {
    const startAlgorithm = Date.now();

    if (!graph.hasOwnProperty(start) || !graph.hasOwnProperty(end)) {
        return {
            workingTime: Date.now() - startAlgorithm,
            distance: -1,
            path: []
        }
    } 

    const p = {};
    p[start] = -1;
    graph[start].marked = true;
    const path = [];

    let q = new Deque([start]);

    while (q.length) {
        let v = q.pop();

        for (let i of graph[v].linked) {
            if (!graph[i].marked) {
                p[i] = v;
                graph[i].marked = true;

                if (i === end) break;

                q.unshift(i);
            }
        }
    }

    if (!graph[end].marked) {
        if (reset) {
            for (let v in graph) {
                graph[v].marked = false;
            }
        }

        return {
            workingTime: Date.now() - startAlgorithm,
            distance: -1,
            path: []
        };
    }

    if (reset) {
        for (let v in graph) {
            graph[v].marked = false;
        }
    }

    let c = end;
    while (c !== -1) {
        path.push(String(c));
        c = p[c];
    }

    path.reverse();

    return {
        workingTime: Date.now() - startAlgorithm,
        distance: path.length - 1,
        path
    }
}