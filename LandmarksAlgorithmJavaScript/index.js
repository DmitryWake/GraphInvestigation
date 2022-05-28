const parse = require("./parse.js");
const distance = require("./distance.js");
const LB = require("./landmraks-basic.js");
const selectLandmarks = require("./select-landmarks.js");
const landmarksSC = require("./landmarks-sc.js");

// let test = parse.parseFromTxt("../Datasets/astro.txt", false);
// console.log(distance.bfs(test, 22134, 79960));

let test = parse.parseFromTxt("../Datasets/test2.txt", false);
// console.log(test);
// let time = Date.now();
// console.log(test);
// console.log(distance.bfs(test, 1, 13));
// console.log(selectLandmarks.highestDegree(test, Object.keys(test).length * 0.1));
// console.log(selectLandmarks.bestCoverage(test, Object.keys(test).length * 0.1, Object.keys(test).length));
// console.log(selectLandmarks.bestCoverageOptimize(test, 1, 1));
// console.log(Date.now() - time);
// time = Date.now();
// console.log(selectLandmarks.bestCoverage(test, 1, 1));
// console.log(selectLandmarks.bfsVisitedVertices(test, 1));
// console.log(selectLandmarks.randomLandmarks(test, Object.keys(test).length * 0.1));
// console.log(Date.now() - time);
// console.log(LB.landmarksBasic(test, 'highest-degree', Object.keys(test).length * 0.1, 22134, 79960));
// console.log(distance.bfs(test, 22134, 79960));

// console.log(landmarksSc.pathTo(test, 8, [3, 4]));

// console.log(landmarksSC.landmarksSC(test, "random", 3, 11, 4));