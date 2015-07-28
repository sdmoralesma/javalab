const CRIMSON_THEME = "ace/theme/crimson_editor";
const SUBLIME_THEME = "ace/theme/monokai";
const JAVA_MODE = "ace/mode/java";

var model = null; // Main Model
var root = null; //Tree Model

var labApp = angular.module('labApp', ['blockUI', 'treeControl', "angucomplete-alt"]);

labApp.factory('MiddlewareClient', function ($http) {

    const ENDPOINT = 'rest/process';
    return {
        initialize: function () {
            const RUN_CODE_SERVICE = ENDPOINT + "/init";
            $http.get(RUN_CODE_SERVICE)
                .then(function (response) {
                    model = response.data;
                    root = new TreeModel().parse({
                        "id": 0,
                        "children": model.treedata
                    });

                    function initializeAceEditorFor(editorVar, editorCode) {
                        editorVar.$blockScrolling = Infinity;
                        editorVar.setTheme(CRIMSON_THEME);
                        editorVar.getSession().setMode(JAVA_MODE);
                        editorVar.getSession().setValue(editorCode);
                    }

                    var codeEditor = ace.edit("code-editor");
                    initializeAceEditorFor(codeEditor, model.treedata[0].children[0].children[0].code);

                }, function error(failure) {
                    alert('error code: ' + failure.status);
                });
        },

        runCode: function (model) {
            const RUN_CODE_SERVICE = ENDPOINT + "/run";
            $http.post(RUN_CODE_SERVICE, model, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data;
            }, function error(failure) {
                alert('error code: ' + failure.status);
            });
        },

        runTest: function (model) {
            const RUN_TESTS_SERVICE = ENDPOINT + "/tests";
            $http.post(RUN_TESTS_SERVICE, model, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data;
            }, function error(failure) {
                alert('error code: ' + failure.status);
            });
        }
    };
});

labApp.controller("mainCtrl", function ($scope, MiddlewareClient, blockUI) {

    $scope.libsModel = null;
    $scope.treeModelMap = null;

    $scope.init = function () {
        MiddlewareClient.initialize();
        $scope.libsModel = model;
        $scope.treeModelMap = new TreeModel().parse(model.treedata);//TODO:fix me!
    };
    $scope.init();



    $scope.formatCode = function () {

        function formatCodeFor(editor) {
            editor.getSession().setUseWrapMode(false);
            var oldFormat = editor.getValue();
            if (oldFormat.trim().length > 0) {
                var newFormat = js_beautify(oldFormat, {
                    'indent_size': 1,
                    'indent_char': '\t'
                });
                editor.setValue(newFormat);
            }
        }

        formatCodeFor(ace.edit("code-editor"));
    };

    $scope.runCode = function () {

        if (model.runnableNode.id == undefined) {
            model.console = "Please select a class to run";
            return;
        }

        blockUI.start();
        $scope.selected.code = codeEditor.getValue();
        $scope.selected.cursor = codeEditor.getCursorPosition();
        model.runnableNode.mainClass = true;
        model.runnableNode.testClass = false;
        model.console = MiddlewareClient.runCode(model);
        blockUI.stop();
    };

    $scope.runTests = function () {

        if (model.runnableNode.id == undefined) {
            model.console = "Please select a test class";
            return;
        }

        blockUI.start();
        model.runnableNode.mainClass = false;
        model.runnableNode.testClass = true;
        model.console = MiddlewareClient.runTest(model);
        blockUI.stop();
    };


    /**
     * Tree manager
     */
    $scope.showSelected = function (node) {

        var oldNode = $scope.selected;
        if (oldNode !== undefined) {
            oldNode.code = codeEditor.getValue();
            oldNode.cursor = codeEditor.getCursorPosition();
        }

        $scope.selected = node;
        if (node.type === "file") {
            codeEditor.setValue(node.code, 1); // moves cursor to the end
            var cursor = node.cursor == undefined ? {column: 0, row: 0} : node.cursor;
            codeEditor.moveCursorToPosition(cursor);
            codeEditor.focus();
        }
    };

    $scope.addNode = function (typeToCreate, node) {

        var nodeName = prompt("Enter " + typeToCreate + " name", typeToCreate + " name");
        if (nodeName == null || nodeName.trim() === "") {
            return;
        }

        var nodeFound = root.first(function (iterNode) {
            return iterNode.model.id === node.id;
        });

        function newNodeUUID() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        }

        function createNewNode() {
            return new TreeModel().parse({id: newNodeUUID(), name: nodeName, type: typeToCreate, children: []});
        }

        function pathAsString(node) {
            var path = "";
            var pathArray = node.getPath();
            for (var index = 2; index < pathArray.length; ++index) {
                if (pathArray[index].model.name != undefined) {

                    if (index === (pathArray.length - 1)) {
                        path += ".";
                    }

                    path += pathArray[index].model.name;
                }
            }
            return path;
        }

        var newNode = createNewNode();
        if (newNode.model.type === "file") {
            nodeFound.parent.addChild(newNode);
            if (nodeName.indexOf(".java") > -1) {
                $scope.javaClasses.push({
                    id: newNode.model.id,
                    name: newNode.model.name,
                    path: pathAsString(newNode)
                })
            }

        } else if (newNode.model.type === "folder") {
            nodeFound.addChild(newNode);
        }
    };

    $scope.editNode = function (node) {

        if (node.id === 1 || node.id === 2) {
            return;
        }


        var nodeName = prompt("Change name", node.name);
        if (nodeName == null || nodeName.trim() === "") {
            return;
        }

        var nodeFound = root.first(function (iterNode) {
            return iterNode.model.id === node.id;
        });

        nodeFound.model.name = nodeName;

        function pathAsString(node) {

            var path = "";
            var pathArray = node.getPath();
            for (var index = 2; index < pathArray.length; ++index) {
                if (pathArray[index].model.name != undefined) {

                    if (index === (pathArray.length - 1)) {
                        path += ".";
                    }

                    path += pathArray[index].model.name;
                }
            }
            return path;
        }

        function updatePaths(node) {
            if (node.model.type === "file" && node.model.name.indexOf(".java") > -1) {
                var classesArray = $scope.javaClasses;
                for (var i = 0; i < classesArray.length; ++i) {
                    if (classesArray[i].id == node.model.id) {
                        classesArray[i].name = node.model.name;
                        classesArray[i].path = pathAsString(node);
                    }
                }

            } else if (node.model.type === "folder") {
                for (var j = 0; j < node.children.length; ++j) {
                    updatePaths(node.children[j]);
                }
            }
        }

        updatePaths(nodeFound);

    };

    $scope.removeNode = function (node) {

        if (node.id === 1 || node.id === 2) {
            return;
        }

        var confirmed = confirm("Delete " + node.type + " '" + node.name + "' ?");
        if (!confirmed) {
            return;
        }

        var nodeFound = root.first(function (iterNode) {
            return iterNode.model.id === node.id;
        });

        $scope.selected = $scope.treedata[0];//root selected

        if (nodeName.indexOf(".java") > -1) {
            var classesArray = $scope.javaClasses;
            for (var index = 0; index < classesArray.length; ++index) {
                if (classesArray[index].id == nodeFound.model.id) {
                    classesArray.splice(index, 1);
                }
            }
        }

        nodeFound.drop();
    };

    $scope.treeOptions = {
        nodeChildren: "children",
        dirSelectable: true,
        allowDeselect: false,
        isLeaf: function (node) {
            return node.type === "file";
        },
        equality: function (node1, node2) {
            return (node1 === node2) && (angular.equals(node1, node2));
        }
    };

    $scope.treedata = model.treedata;

    $scope.expandedNodes = [$scope.treedata[0], $scope.treedata[0].children[0], $scope.treedata[1], $scope.treedata[1].children[0]];

    $scope.selected = $scope.treedata[0].children[0].children[0];


    /**
     * Autocompletion
     */
    $scope.selectedFile = function (selected) {

        if (selected == undefined) {
            model.runnableNode.id = undefined;
            return;
        }

        var nodeFound = root.first(function (iterNode) {
            return iterNode.model.id === selected.originalObject.id;
        });

        model.runnableNode.id = nodeFound.model.id;
    };

    $scope.javaClasses = [
        {name: 'HelloWorld.java', path: 'com.company.project.HelloWorld.java', id: 111},
        {name: 'HelloWorldTest.java', path: 'com.company.project.HelloWorldTest.java', id: 211}
    ]

});