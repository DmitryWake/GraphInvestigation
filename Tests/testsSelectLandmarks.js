const parse = require("../Code/parse.js");
const selectLandmarks = require("../Code/select-landmarks.js");
const jsonfile = require('jsonfile');

const astro = {
    random20: [],
    random60: [],
    random100: [],
    highestDegree20: [],
    highestDegree60: [],
    highestDegree100: [],
    bestCoverage20: [],
    bestCoverage60: [],
    bestCoverage100: [],
    random20Time: 0,
    random60Time: 0,
    random100Time: 0,
    highestDegree20Time: 0,
    highestDegree60Time: 0,
    highestDegree100Time: 0,
    bestCoverage20Time: 0,
    bestCoverage60Time: 0,
    bestCoverage100Time: 0,
}

let astroGraph = parse.parseFromTxt("../Datasets/astro.txt", false);

let timestamp = Date.now();
astro.random20 = selectLandmarks.randomLandmarks(astroGraph, 20);
astro.random20Time = Date.now() - timestamp;

timestamp = Date.now();
astro.random60 = selectLandmarks.randomLandmarks(astroGraph, 60);
astro.random60Time = Date.now() - timestamp;

timestamp = Date.now();
astro.random100 = selectLandmarks.randomLandmarks(astroGraph, 100);
astro.random100Time = Date.now() - timestamp;

timestamp = Date.now();
astro.highestDegree20 = selectLandmarks.highestDegree(astroGraph, 20);
astro.highestDegree20Time = Date.now() - timestamp;

timestamp = Date.now();
astro.highestDegree60 = selectLandmarks.highestDegree(astroGraph, 60);
astro.highestDegree60Time = Date.now() - timestamp;

timestamp = Date.now();
astro.highestDegree100 = selectLandmarks.highestDegree(astroGraph, 100);
astro.highestDegree100Time = Date.now() - timestamp;

timestamp = Date.now();
astro.bestCoverage20 = selectLandmarks.bestCoverage(astroGraph, 20, 500);
astro.bestCoverage20Time = Date.now() - timestamp;

timestamp = Date.now();
astro.bestCoverage60 = selectLandmarks.bestCoverage(astroGraph, 60, 500);
astro.bestCoverage60Time = Date.now() - timestamp;

timestamp = Date.now();
astro.bestCoverage100 = selectLandmarks.bestCoverage(astroGraph, 100, 500);
astro.bestCoverage100Time = Date.now() - timestamp;
 
jsonfile.writeFileSync("../JSON/astroLandmarks.json", astro, { spaces: 2, EOL: '\r\n' });

/////////////////////////////////////////////////////////////////////////////////////////

const google = {
    random20: [],
    random60: [],
    random100: [],
    highestDegree20: [],
    highestDegree60: [],
    highestDegree100: [],
    bestCoverage20: [],
    bestCoverage60: [],
    bestCoverage100: [],
    random20Time: 0,
    random60Time: 0,
    random100Time: 0,
    highestDegree20Time: 0,
    highestDegree60Time: 0,
    highestDegree100Time: 0,
    bestCoverage20Time: 0,
    bestCoverage60Time: 0,
    bestCoverage100Time: 0,
}

let googleGraph = parse.parseFromTxt("../Datasets/google.txt", false);

timestamp = Date.now();
google.random20 = selectLandmarks.randomLandmarks(googleGraph, 20);
google.random20Time = Date.now() - timestamp;

timestamp = Date.now();
google.random60 = selectLandmarks.randomLandmarks(googleGraph, 60);
google.random60Time = Date.now() - timestamp;

timestamp = Date.now();
google.random100 = selectLandmarks.randomLandmarks(googleGraph, 100);
google.random100Time = Date.now() - timestamp;

timestamp = Date.now();
google.highestDegree20 = selectLandmarks.highestDegree(googleGraph, 20);
google.highestDegree20Time = Date.now() - timestamp;

timestamp = Date.now();
google.highestDegree60 = selectLandmarks.highestDegree(googleGraph, 60);
google.highestDegree60Time = Date.now() - timestamp;

timestamp = Date.now();
google.highestDegree100 = selectLandmarks.highestDegree(googleGraph, 100);
google.highestDegree100Time = Date.now() - timestamp;

timestamp = Date.now();
google.bestCoverage20 = selectLandmarks.bestCoverage(googleGraph, 20, 500);
google.bestCoverage20Time = Date.now() - timestamp;

timestamp = Date.now();
google.bestCoverage60 = selectLandmarks.bestCoverage(googleGraph, 60, 500);
google.bestCoverage60Time = Date.now() - timestamp;

timestamp = Date.now();
google.bestCoverage100 = selectLandmarks.bestCoverageOptimize(googleGraph, 100, 500);
google.bestCoverage100Time = Date.now() - timestamp;
 
jsonfile.writeFileSync("../JSON/googleLandmarks.json", google, { spaces: 2, EOL: '\r\n' });

///////////////////////////////////////////////////////////////////////////////////////////

const vk = {
    random20: [],
    random60: [],
    random100: [],
    highestDegree20: [],
    highestDegree60: [],
    highestDegree100: [],
    bestCoverage20: [],
    bestCoverage60: [],
    bestCoverage100: [],
    random20Time: 0,
    random60Time: 0,
    random100Time: 0,
    highestDegree20Time: 0,
    highestDegree60Time: 0,
    highestDegree100Time: 0,
    bestCoverage20Time: 0,
    bestCoverage60Time: 0,
    bestCoverage100Time: 0,
}

let vkGraph = parse.parseFromTxt("../Datasets/vk.txt", false, " ");

timestamp = Date.now();
vk.random20 = selectLandmarks.randomLandmarks(vkGraph, 20);
vk.random20Time = Date.now() - timestamp;

timestamp = Date.now();
vk.random60 = selectLandmarks.randomLandmarks(vkGraph, 60);
vk.random60Time = Date.now() - timestamp;

timestamp = Date.now();
vk.random100 = selectLandmarks.randomLandmarks(vkGraph, 100);
vk.random100Time = Date.now() - timestamp;

timestamp = Date.now();
vk.highestDegree20 = selectLandmarks.highestDegree(vkGraph, 20);
vk.highestDegree20Time = Date.now() - timestamp;

timestamp = Date.now();
vk.highestDegree60 = selectLandmarks.highestDegree(vkGraph, 60);
vk.highestDegree60Time = Date.now() - timestamp;

timestamp = Date.now();
vk.highestDegree100 = selectLandmarks.highestDegree(vkGraph, 100);
vk.highestDegree100Time = Date.now() - timestamp;

timestamp = Date.now();
google.bestCoverage20 = selectLandmarks.bestCoverage(googleGraph, 20, 500);
google.bestCoverage20Time = Date.now() - timestamp;

timestamp = Date.now();
google.bestCoverage60 = selectLandmarks.bestCoverage(googleGraph, 60, 500);
google.bestCoverage60Time = Date.now() - timestamp;

timestamp = Date.now();
vk.bestCoverage100 = selectLandmarks.bestCoverageOptimize(vkGraph, 100, 500);
vk.bestCoverage100Time = Date.now() - timestamp;
 
jsonfile.writeFileSync("../JSON/vkLandmarks.json", vk, { spaces: 2, EOL: '\r\n' });