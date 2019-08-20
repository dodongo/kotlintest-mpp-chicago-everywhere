var kotlin_test = require('./build/node_modules/kotlin-test.js');

console.log("weeee");

kotlin_test.setAdapter({
    suite: function (name, ignored, fn) {
        console.log("hellooo " + name);
        // if (name == "FunSpec" ||
        //     name == "BehaviorSpec" ||
        //     name == "SuiteSpec" ||
        //     name == "StringSpec")
        //     return;
       // QUnit.module(name, fn);
    },
    test: function (name, ignored, fn) {
        console.log("HELLLLLLLO " + name);
        // if (name === "kotlintestGenerateTests3") {
        //    fn()
        // }
    }
});
