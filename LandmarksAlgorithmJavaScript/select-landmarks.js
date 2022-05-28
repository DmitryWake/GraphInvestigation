const distance = require("./distance.js");
const Deque = require("collections/deque");

exports.randomLandmarks = function (graph, numLand, U = []) { // Метод выбирающий ориентиры случайно
    while (U.length < numLand) {
        let u = Object.keys(graph)[Math.floor(Math.random() * Object.keys(graph).length)];

        if (!U.includes(u)) {
            U.push(u);
        }
    }

    return U;
}

exports.highestDegree = function (graph, numLand) { 
    const degrees = [];
    const U = [];

    for (let v in graph) {
        degrees.push([v, graph[v].linked.length]);
    }

    degrees.sort((a, b) => b[1] - a[1]);

    for (let i = 0; i < numLand; i++) {
        U.push(degrees[i][0]);
    }

    return U;
}

exports.bestCoverage = function (graph, numLand, M) {
    let P = [];

    for (let i = 0; i < M; i++) {
        let s = Object.keys(graph)[Math.floor(Math.random() * Object.keys(graph).length)];
        let t = Object.keys(graph)[Math.floor(Math.random() * Object.keys(graph).length)];

        let path = distance.bfs(graph, s, t).path;

        while (path.length <= 1) {
            s = Object.keys(graph)[Math.floor(Math.random() * Object.keys(graph).length)];
            t = Object.keys(graph)[Math.floor(Math.random() * Object.keys(graph).length)];
            path = distance.bfs(graph, s, t).path;
        }

        P.push(path);
    }

    let Vp = new Set();

    for (let i = 0; i < P.length; i++) {
        for (let j = 0; j < P[i].length; j++) {
            Vp.add(+P[i][j]);
        }
    }

    const U = [];

    for (let k = 0; k < numLand; k++) {
        if (!P.length) {
            exports.randomLandmarks(graph, numLand, U);
            break;
        }

        let c = {};

        for (let v of Vp) {
            c[v] = 0;
        }

        for (let i = 0; i < P.length; i++) {
            for (let j = 0; j < P[i].length; j++) {
                c[P[i][j]]++;
            }
        }

        let max = 0, argmax = 0;
        for (let v in c) {
            if (c[v] > max) {
                argmax = v;
                max = c[v];
            }
        }

        U.push(argmax);
        Vp.delete(+argmax);

        P = P.filter(path => !path.includes(argmax));
    }

    return U;
}

exports.bfsVisitedVertices = function (graph, start, reset = true) {
    const p = {};
    p[start] = -1;
    graph[start].marked = true;
    const visitedVertices = [];

    let q = new Deque([start]);

    while (q.length) {
        let v = q.pop();

        for (let i of graph[v].linked) {
            if (!graph[i].marked) {
                p[i] = v;
                graph[i].marked = true;
                visitedVertices.push([i, []]);
                q.unshift(i);
            }
        }
    }

    if (reset) {
        for (let v in graph) {
            graph[v].marked = false;
        }
    }

    for (let i of visitedVertices) {
        let c = i[0];

        while (c !== -1) {
            i[1].push(String(c));
            c = p[c];
        }
        i[1].reverse();
    }

    return visitedVertices;
}


exports.bestCoverageOptimize = function (graph, numLand, M) {
    let P = [];

    for (let i = 0; i < M; i++) {
        // console.log('Cтарт поиска пути опт');
        // let start = Date.now();

        let s = Object.keys(graph)[Math.floor(Math.random() * Object.keys(graph).length)];

        let visitedVerticesFromS = exports.bfsVisitedVertices(graph, s);

        // Можем взять путь до рандомной вершины
        // let path = visitedVerticesFromS[Math.floor(Math.random() * visitedVerticesFromS.length)][1];

        // Берем путь рандомной вершины, до которой путь самый длинный
        let maxLength = visitedVerticesFromS[visitedVerticesFromS.length - 1][1].length;
        visitedVerticesFromS = visitedVerticesFromS.filter(item => item[1].length === maxLength);
        let path = visitedVerticesFromS[Math.floor(Math.random() * visitedVerticesFromS.length)][1];

        P.push(path);

        // console.log(`Путь ${i} был найден за ${Date.now() - start}`);
    }

    let Vp = new Set();

    for (let i = 0; i < P.length; i++) {
        for (let j = 0; j < P[i].length; j++) {
            Vp.add(+P[i][j]);
        }
    }

    const U = [];

    for (let k = 0; k < numLand; k++) {
        if (!P.length) {
            exports.randomLandmarks(graph, numLand, U);
            break;
        }

        let c = {};

        for (let v of Vp) {
            c[v] = 0;
        }

        for (let i = 0; i < P.length; i++) {
            for (let j = 0; j < P[i].length; j++) {
                c[P[i][j]]++;
            }
        }

        let max = 0, argmax = 0;
        for (let v in c) {
            if (c[v] > max) {
                argmax = v;
                max = c[v];
            }
        }

        U.push(argmax);
        Vp.delete(+argmax);

        P = P.filter(path => !path.includes(argmax));
    }

    return U;
}
