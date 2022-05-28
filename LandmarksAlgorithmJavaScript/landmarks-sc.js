const Deque = require("collections/deque");
const selectLandmarks = require("./select-landmarks.js");
const landmarksBasic = require("./landmraks-basic.js");

// Декартово произведение.
function cartesianProduct(...arrays) {
	function _inner(...args) {
		if (arguments.length > 1) {
			let arr2 = args.pop(); // arr of arrs of elems
			let arr1 = args.pop(); // arr of elems
			return _inner(...args,
				arr1.map(e1 => arr2.map(e2 => [e1, ...e2]))
				    .reduce((arr, e) => arr.concat(e), [])
			);
		} else {
			return args[0];
		}
	};
	return _inner(...arrays, [[]]);
};

// Находит пересечение ребер графа и переданных ребер.
function intersectionEdgesWithE(graph, edges) {
    let result = [];

    for (let e of edges) {
        if (graph[e[0]].linked.includes(+e[1])) {
            result.push(e);
        }
    }

    return result;
}

// Ищет кратчайщий путь от вершины до другого пути.
exports.pathTo = function (graph, s, pi, reset = true) {
    if (!graph.hasOwnProperty(s) || !pi.length) {
        return [];
    }

    if (pi.includes(String(s))) {
        return [s];
    }

    const p = {};
    p[s] = -1;
    graph[s].marked = true;
    const path = [];

    let q = new Deque([s]);
    let t; // Ближайщая точка к s, принадлежащая пути.

    while (q.length) {
        let v = q.pop();

        for (let i of graph[v].linked) {
            if (!graph[i].marked) {
                p[i] = v;
                graph[i].marked = true;

                if (pi.includes(String(i))) {
                    t = i;
                    break;
                }

                q.unshift(i);
            }
        }
    }

    const checkMarked = (v) => graph[v].marked;

    if (!pi.some(checkMarked)) {
        if (reset) {
            for (let v in graph) {
                graph[v].marked = false;
            }
        }

        return [];
    }

    if (reset) {
        for (let v in graph) {
            graph[v].marked = false;
        }
    }

    let c = t;
    while (c !== -1) {
        path.push(String(c));
        c = p[c];
    }

    path.reverse();

    return path;
}

exports.distanceSC = function (graph, u, s, t) {
    let path1 = exports.pathTo(graph, s, [u]);
    let path2 = exports.pathTo(graph, t, path1);
    let LCA = path2[path2.length - 1];
    let path3 = exports.pathTo(graph, s, [LCA]);
    let best = path2.length - 1 + path3.length - 1;

    for (let w of intersectionEdgesWithE(graph, cartesianProduct(path2, path3).concat(cartesianProduct(path3, path2)))) {
        let current = exports.pathTo(graph, String(s), [String(w[0])]).length - 1 + 1 + exports.pathTo(graph, String(w[1]), [String(t)]).length - 1;
        best = Math.min(current, best);
    }

    return best;
}

//Алгоритм landmark-SC. Можно передать уже готовый список ориентиров, тогда они считаться заново не будут.
//Возвращает оценку расстояния и время работы(во время работы поиск ориентиров не включено).
exports.landmarksSC = function (graph, s, t, numLandmarks = 20, method = "random", U = [], M = 500) {
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
        U = landmarksBasic.precompute(graph, U, method, numLandmarks, M);
    }

    let time = Date.now();

    let d = Infinity;
    let nearestU;

    for (let u of U) {
        if (graph[s].distances[u] && graph[t].distances[u]) {
            if (d > graph[s].distances[u] + graph[t].distances[u]) {
                d = graph[s].distances[u] + graph[t].distances[u];
                nearestU = u;
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

    if (d != Infinity) {
        return {
            distance: exports.distanceSC(graph, nearestU, s, t),
            workingTime: Date.now() - time
        };
    } else {
        return {
            distance: -1,
            workingTime: Date.now() - time
        };
    }
}