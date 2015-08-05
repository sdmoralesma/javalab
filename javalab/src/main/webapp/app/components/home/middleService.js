'use strict';
labApp.factory('middleService', ['$http', '$rootScope', function ($http, $rootScope) {

    const ENDPOINT = 'rest/process';
    return {
        initialize: function () {
            const INIT_SERVICE = ENDPOINT + "/init";
            $http.get(INIT_SERVICE)
                .then(function (response) {
                    $rootScope.appModel = response.data;

                    $rootScope.root = new TreeModel().parse({"id": 0, "children": $rootScope.appModel.treedata});

                    //tree data initialization
                    $rootScope.treedata = $rootScope.appModel.treedata;
                    $rootScope.expandedNodes = [$rootScope.treedata[0], $rootScope.treedata[0].children[0], $rootScope.treedata[1], $rootScope.treedata[1].children[0]];
                    $rootScope.selected = $rootScope.treedata[0].children[0].children[0];

                    //AutoCompletion
                    $rootScope.javaClasses = [
                        {name: 'HelloWorld.java', path: 'com.company.project.HelloWorld.java', id: 111},
                        {name: 'HelloWorldTest.java', path: 'com.company.project.HelloWorldTest.java', id: 211}
                    ];

                    (function initializeCodeEditor() {
                        const CRIMSON_THEME = "ace/theme/crimson_editor";
                        const JAVA_MODE = "ace/mode/java";

                        $rootScope.codeEditor = ace.edit("code-editor");
                        $rootScope.codeEditor.$blockScrolling = Infinity;
                        $rootScope.codeEditor.setTheme(CRIMSON_THEME);
                        $rootScope.codeEditor.getSession().setMode(JAVA_MODE);
                        $rootScope.codeEditor.getSession().setValue($rootScope.appModel.treedata[0].children[0].children[0].code);
                    }());

                }, function (failure) {
                    alert('error initializing: ' + failure.status);
                });
        },

        runCode: function (model) {
            const RUN_CODE_SERVICE = ENDPOINT + "/run";
            $http.post(RUN_CODE_SERVICE, model, {
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data;
            }, function (failure) {
                alert('error code: ' + failure.status);
            });
        },

        runTest: function (model) {
            const RUN_TESTS_SERVICE = ENDPOINT + "/tests";
            $http.post(RUN_TESTS_SERVICE, model, {
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data;
            }, function (failure) {
                alert('error code: ' + failure.status);
            });
        }
    };
}]);