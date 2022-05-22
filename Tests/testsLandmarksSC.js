const jsonfile = require('jsonfile');
const parse = require("../Code/parse.js");
const landmarksSC = require("../Code/landmarks-sc.js");
const landmarksBasic = require("../Code/landmraks-basic.js");
const distance = require("../Code/distance.js");
const _ = require("lodash");
const microtime = require("microtime");

//Тесты astro графа
const astroGraph = parse.parseFromTxt("../Datasets/astro.txt", false);
const astro = jsonfile.readFileSync("../JSON/astroLandmarks.json");

for (let numLandmarks = 20; numLandmarks <= 100; numLandmarks += 40) {
    let accuracyRandom = 0;
    let accuracyDegree = 0;
    let accuracyCoverage = 0;

    let astroGraphRandom = _.cloneDeep(astroGraph);
    landmarksBasic.precompute(astroGraphRandom, astro[`random${numLandmarks}`]);

    let astroGraphDegree = _.cloneDeep(astroGraph);
    landmarksBasic.precompute(astroGraphDegree, astro[`highestDegree${numLandmarks}`]);

    let astroGraphCoverage = _.cloneDeep(astroGraph);
    landmarksBasic.precompute(astroGraphCoverage, astro[`bestCoverage${numLandmarks}`]);

    let timeBFS = 0;
    let timeRandom = 0;
    let timeDegree = 0;
    let timeCoverage = 0;
    let time = 0;
    

    for (let i = 0; i < 500; i++) {
        console.log(`Итерация ${i}`);
        let s = Object.keys(astroGraph)[Math.floor(Math.random() * Object.keys(astroGraph).length)];
        let t = Object.keys(astroGraph)[Math.floor(Math.random() * Object.keys(astroGraph).length)];
    
        time = microtime.now();
        let resultBFS = distance.bfs(astroGraph, s, t).distance;
        timeBFS += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksSCRandom = landmarksSC.landmarksSC(astroGraphRandom, s, t, astro[`random${numLandmarks}`], numLandmarks, "random");
        timeRandom += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksSCDegree = landmarksSC.landmarksSC(astroGraphDegree, s, t, astro[`highestDegree${numLandmarks}`], numLandmarks, "highest-degree");
        timeDegree += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksSCCoverage = landmarksSC.landmarksSC(astroGraphCoverage, s, t, astro[`bestCoverage${numLandmarks}`], numLandmarks, "best-coverage");
        timeCoverage += microtime.now() - time;

        accuracyRandom += (resultLandmarksSCRandom - resultBFS) / resultBFS;
        accuracyDegree += (resultLandmarksSCDegree - resultBFS) / resultBFS;
        accuracyCoverage += (resultLandmarksSCCoverage - resultBFS) / resultBFS;
    }

    astro[`accuracyRandom${numLandmarks}`] = accuracyRandom / 500;
    astro[`random${numLandmarks}SCAverageTimeUs`] = timeRandom / 500;

    astro[`accuracyDegree${numLandmarks}`] = accuracyDegree / 500;
    astro[`highestDegree${numLandmarks}SCAverageTimeUs`] = timeDegree / 500;

    astro[`accuracyCoverage${numLandmarks}`] = accuracyCoverage / 500;
    astro[`bestCoverage${numLandmarks}SCAverageTimeUs`] = timeCoverage / 500;
}

jsonfile.writeFileSync("../JSON/astroSC.json", astro, { spaces: 2, EOL: '\r\n' });

////////////////////////////////////////////////////////////////////////////////
//Тесты google графа
const googleGraph = parse.parseFromTxt("../Datasets/google.txt", false);
const google = jsonfile.readFileSync("../JSON/googleLandmarks.json");

for (let numLandmarks = 20; numLandmarks <= 100; numLandmarks += 40) {
    let accuracyRandom = 0;
    let accuracyDegree = 0;
    let accuracyCoverage = 0;

    let googleGraphRandom = _.cloneDeep(googleGraph);
    landmarksBasic.precompute(googleGraphRandom, google[`random${numLandmarks}`]);

    let googleGraphDegree = _.cloneDeep(googleGraph);
    landmarksBasic.precompute(googleGraphDegree, google[`highestDegree${numLandmarks}`]);

    let googleGraphCoverage = _.cloneDeep(googleGraph);
    landmarksBasic.precompute(googleGraphCoverage, google[`bestCoverage${numLandmarks}`]);

    let timeBFS = 0;
    let timeRandom = 0;
    let timeDegree = 0;
    let timeCoverage = 0;
    let time = 0;
    

    for (let i = 0; i < 500; i++) {
        console.log(`Итерация ${i}`);
        let s = Object.keys(googleGraph)[Math.floor(Math.random() * Object.keys(googleGraph).length)];
        let t = Object.keys(googleGraph)[Math.floor(Math.random() * Object.keys(googleGraph).length)];
    
        time = microtime.now();
        let resultBFS = distance.bfs(googleGraph, s, t).distance;
        timeBFS += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksSCRandom = landmarksSC.landmarksSC(googleGraphRandom, s, t, google[`random${numLandmarks}`], numLandmarks, "random");
        timeRandom += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksSCDegree = landmarksSC.landmarksSC(googleGraphDegree, s, t, google[`highestDegree${numLandmarks}`], numLandmarks, "highest-degree");
        timeDegree += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksSCCoverage = landmarksSC.landmarksSC(googleGraphCoverage, s, t, google[`bestCoverage${numLandmarks}`], numLandmarks, "best-coverage");
        timeCoverage += microtime.now() - time;

        accuracyRandom += Math.abs(resultLandmarksSCRandom - resultBFS) / resultBFS;
        accuracyDegree += Math.abs(resultLandmarksSCDegree - resultBFS) / resultBFS;
        accuracyCoverage += Math.abs(resultLandmarksSCCoverage - resultBFS) / resultBFS;
    }

    google[`accuracyRandom${numLandmarks}`] = accuracyRandom / 500;
    google[`random${numLandmarks}SCAverageTimeUs`] = timeRandom / 500;

    google[`accuracyDegree${numLandmarks}`] = accuracyDegree / 500;
    google[`highestDegree${numLandmarks}SCAverageTimeUs`] = timeDegree / 500;

    google[`accuracyCoverage${numLandmarks}`] = accuracyCoverage / 500;
    google[`bestCoverage${numLandmarks}SCAverageTimeUs`] = timeCoverage / 500;
    
    jsonfile.writeFileSync(`../JSON/googleSC${numLandmarks}.json`, google, { spaces: 2, EOL: '\r\n' });
}

jsonfile.writeFileSync("../JSON/googleSC.json", google, { spaces: 2, EOL: '\r\n' });