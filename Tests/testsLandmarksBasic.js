const jsonfile = require('jsonfile');
const parse = require("../Code/parse.js");
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
        let resultLandmarksBasicRandom = landmarksBasic.landmarksBasic(astroGraphRandom, s, t, astro[`random${numLandmarks}`], numLandmarks, "random");
        timeRandom += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicDegree = landmarksBasic.landmarksBasic(astroGraphDegree, s, t, astro[`highestDegree${numLandmarks}`], numLandmarks, "highest-degree");
        timeDegree += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicCoverage = landmarksBasic.landmarksBasic(astroGraphCoverage, s, t, astro[`bestCoverage${numLandmarks}`], numLandmarks, "best-coverage");
        timeCoverage += microtime.now() - time;

        accuracyRandom += (resultLandmarksBasicRandom - resultBFS) / resultBFS;
        accuracyDegree += (resultLandmarksBasicDegree - resultBFS) / resultBFS;
        accuracyCoverage += (resultLandmarksBasicCoverage - resultBFS) / resultBFS;
    }

    astro[`accuracyRandom${numLandmarks}`] = accuracyRandom / 500;
    astro[`random${numLandmarks}BasicAverageTimeUs`] = timeRandom / 500;

    astro[`accuracyDegree${numLandmarks}`] = accuracyDegree / 500;
    astro[`highestDegree${numLandmarks}BasicAverageTimeUs`] = timeDegree / 500;

    astro[`accuracyCoverage${numLandmarks}`] = accuracyCoverage / 500;
    astro[`bestCoverage${numLandmarks}BasicAverageTimeUs`] = timeCoverage / 500;
}

jsonfile.writeFileSync("../JSON/astroBasic.json", astro, { spaces: 2, EOL: '\r\n' });

/////////////////////////////////////////////////////////////////////////////////////////
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
        console.log(`Итерация ${i} google`);
        let s = Object.keys(googleGraph)[Math.floor(Math.random() * Object.keys(googleGraph).length)];
        let t = Object.keys(googleGraph)[Math.floor(Math.random() * Object.keys(googleGraph).length)];
    
        time = microtime.now();
        let resultBFS = distance.bfs(googleGraph, s, t).distance;
        timeBFS += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicRandom = landmarksBasic.landmarksBasic(googleGraphRandom, s, t, google[`random${numLandmarks}`], numLandmarks, "random");
        timeRandom += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicDegree = landmarksBasic.landmarksBasic(googleGraphDegree, s, t, google[`highestDegree${numLandmarks}`], numLandmarks, "highest-degree");
        timeDegree += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicCoverage = landmarksBasic.landmarksBasic(googleGraphCoverage, s, t, google[`bestCoverage${numLandmarks}`], numLandmarks, "best-coverage");
        timeCoverage += microtime.now() - time;

        accuracyRandom += Math.abs(resultLandmarksBasicRandom - resultBFS) / resultBFS;
        accuracyDegree += Math.abs(resultLandmarksBasicDegree - resultBFS) / resultBFS;
        accuracyCoverage += Math.abs(resultLandmarksBasicCoverage - resultBFS) / resultBFS;
    }

    google[`accuracyRandom${numLandmarks}`] = accuracyRandom / 500;
    google[`random${numLandmarks}BasicAverageTimeUs`] = timeRandom / 500;

    google[`accuracyDegree${numLandmarks}`] = accuracyDegree / 500;
    google[`highestDegree${numLandmarks}BasicAverageTimeUs`] = timeDegree / 500;

    google[`accuracyCoverage${numLandmarks}`] = accuracyCoverage / 500;
    google[`bestCoverage${numLandmarks}BasicAverageTimeUs`] = timeCoverage / 500;
    jsonfile.writeFileSync(`../JSON/googleBasic${numLandmarks}.json`, google, { spaces: 2, EOL: '\r\n' });
}

jsonfile.writeFileSync("../JSON/googleBasic.json", google, { spaces: 2, EOL: '\r\n' });

/////////////////////////////////////////////////////////////////////////////////////////
// Тесты vk графа
const vkGraph = parse.parseFromTxt("../Datasets/vk.txt", false, " ");
const vk = jsonfile.readFileSync("../JSON/vkLandmarks.json");

for (let numLandmarks = 20; numLandmarks <= 100; numLandmarks += 40) {
    let accuracyRandom = 0;
    let accuracyDegree = 0;
    let accuracyCoverage = 0;

    let vkGraphRandom = _.cloneDeep(vkGraph);
    landmarksBasic.precompute(vkGraphRandom, vk[`random${numLandmarks}`]);

    let vkGraphDegree = _.cloneDeep(vkGraph);
    landmarksBasic.precompute(vkGraphDegree, vk[`highestDegree${numLandmarks}`]);

    let vkGraphCoverage = _.cloneDeep(vkGraph);
    landmarksBasic.precompute(vkGraphCoverage, vk[`bestCoverage${numLandmarks}`]);

    let timeBFS = 0;
    let timeRandom = 0;
    let timeDegree = 0;
    let timeCoverage = 0;
    let time = 0;
    

    for (let i = 0; i < 100; i++) {
        console.log(`Итерация ${i} vk`);
        let s = Object.keys(vkGraph)[Math.floor(Math.random() * Object.keys(vkGraph).length)];
        let t = Object.keys(vkGraph)[Math.floor(Math.random() * Object.keys(vkGraph).length)];
    
        time = microtime.now();
        let resultBFS = distance.bfs(vkGraph, s, t).distance;
        timeBFS += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicRandom = landmarksBasic.landmarksBasic(vkGraphRandom, s, t, vk[`random${numLandmarks}`], numLandmarks, "random");
        timeRandom += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicDegree = landmarksBasic.landmarksBasic(vkGraphDegree, s, t, vk[`highestDegree${numLandmarks}`], numLandmarks, "highest-degree");
        timeDegree += microtime.now() - time;

        time = microtime.now();
        let resultLandmarksBasicCoverage = landmarksBasic.landmarksBasic(vkGraphCoverage, s, t, vk[`bestCoverage${numLandmarks}`], numLandmarks, "best-coverage");
        timeCoverage += microtime.now() - time;

        accuracyRandom += (resultLandmarksBasicRandom - resultBFS) / resultBFS;
        accuracyDegree += (resultLandmarksBasicDegree - resultBFS) / resultBFS;
        accuracyCoverage += (resultLandmarksBasicCoverage - resultBFS) / resultBFS;
    }

    vk[`accuracyRandom${numLandmarks}`] = accuracyRandom / 100;
    vk[`random${numLandmarks}BasicAverageTimeUs`] = timeRandom / 100;

    vk[`accuracyDegree${numLandmarks}`] = accuracyDegree / 100;
    vk[`highestDegree${numLandmarks}BasicAverageTimeUs`] = timeDegree / 100;

    vk[`accuracyCoverage${numLandmarks}`] = accuracyCoverage / 100;
    vk[`bestCoverage${numLandmarks}BasicAverageTimeUs`] = timeCoverage / 100;
}

jsonfile.writeFileSync("../JSON/vkBasic.json", vk, { spaces: 2, EOL: '\r\n' });