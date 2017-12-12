const java = require('java');
const fs = require('fs');
const path = require('path');

java.options.push('-Djava.awt.headless=true');

const libPath = __dirname + '/lib';
const jars = fs.readdirSync(libPath);
jars.forEach(function(jar) {
    java.classpath.push(path.join(libPath, jar));
});

module.exports = java;