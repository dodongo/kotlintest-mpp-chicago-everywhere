var kotlin_test = require('./build/node_modules/kotlin-test.js');

kotlin_test.setAdapter({
    suite: function (name, ignored, fn) {
       describe(name, fn);
    },
    test: function (name, ignored, fn) {
         if (name === "kotlintestGenerateTests") {
            fn()
         }
    }
});
