const parse = require("./parse.js");
const distance = require("./distance.js");
const selectLandmarks = require("./select-landmarks.js");
const LB = require("./landmraks-basic.js");
const LSC = require("./landmarks-sc.js");

//const lendmarks = jsonfile.readFileSync("../JSON/landmarks-1653163603682.json");

let test = parse.parseFromTxt("../Datasets/astro.txt", false);
console.log(distance.bfs(test, 22134, 79960));
console.log(LB.landmarksBasic(test, 22134, 79960, 100, "best-coverage"));
console.log(LSC.landmarksSC(test, 22134, 79960, 100, "best-coverage"));