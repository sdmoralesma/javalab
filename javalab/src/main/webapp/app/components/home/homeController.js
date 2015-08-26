(function () {
    'use strict';

    angular.module('LabApp').controller("HomeCtrl", HomeCtrl);

    HomeCtrl.$inject = ['$rootScope', '$scope', 'middleService', 'blockUI', 'initData'];

    function HomeCtrl($rootScope, $scope, middleService, blockUI, initData) {

        var self = this;

        self.findById = function (id) {
            return $scope.root.first(function (iterNode) {
                return iterNode.model.id === id;
            });
        };

        self.initializeCodeEditor = function () {
            const CRIMSON_THEME = "ace/theme/crimson_editor";
            const JAVA_MODE = "ace/mode/java";
            $scope.codeEditor = ace.edit("code-editor");
            $scope.codeEditor.$blockScrolling = Infinity;
            $scope.codeEditor.setTheme(CRIMSON_THEME);
            $scope.codeEditor.getSession().setMode(JAVA_MODE);
            $scope.codeEditor.getSession().setValue($scope.selected.code);
        };

        $scope.appModel = initData.data;

        $scope.$on('$routeChangeSuccess', function () {// initialization of main model
            $scope.root = new TreeModel().parse({"id": 0, "children": $scope.appModel.treedata});

            //tree data initialization
            $scope.treedata = $scope.appModel.treedata;
            $scope.expandedNodes = [self.findById(2).model, self.findById(21).model, self.findById(3).model, self.findById(31).model];
            $scope.selected = self.findById(211).model;

            //AutoCompletion
            $scope.javaClasses = [
                {name: 'HelloWorld.java', path: 'com.company.project.HelloWorld.java', id: 211},
                {name: 'HelloWorldTest.java', path: 'com.company.project.HelloWorldTest.java', id: 311}
            ];
            $scope.initialValue = $scope.javaClasses[0];

            $rootScope.$broadcast('resize', {});

            self.initializeCodeEditor();
        });

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

            formatCodeFor($scope.codeEditor);
        };

        $scope.runCode = function () {
            if ($scope.appModel.runnableNode.id == undefined) {
                $scope.appModel.console = "Please select a class to run";
                return;
            }

            blockUI.start();
            $scope.selected.code = $scope.codeEditor.getValue();
            $scope.selected.cursor = $scope.codeEditor.getCursorPosition();
            $scope.appModel.runnableNode.mainClass = true;
            $scope.appModel.runnableNode.testClass = false;
            $scope.appModel.console = middleService.runCode($scope.appModel);
            blockUI.stop();
        };

        $scope.runTests = function () {
            if ($scope.appModel.runnableNode.id == undefined) {
                $scope.appModel.console = "Please select a test class";
                return;
            }

            blockUI.start();
            $scope.selected.code = $scope.codeEditor.getValue();
            $scope.selected.cursor = $scope.codeEditor.getCursorPosition();
            $scope.appModel.runnableNode.mainClass = false;
            $scope.appModel.runnableNode.testClass = true;
            $scope.appModel.console = middleService.runTest($scope.appModel);
            blockUI.stop();
        };

        $scope.save = function () {
            blockUI.start();
            $scope.selected.code = $scope.codeEditor.getValue();
            $scope.selected.cursor = $scope.codeEditor.getCursorPosition();
            $scope.appModel.console = middleService.saveWorkspace($scope.appModel);
            blockUI.stop();
        };

        $scope.new = function () {
            blockUI.start();
            $scope.appModel.console = middleService.newWorkspace($scope.appModel);
            blockUI.stop();
        };

        /**
         * Tree manager
         */
        $scope.showSelected = function (node) {
            var oldNode = $scope.selected;
            if (oldNode !== undefined) {
                oldNode.code = $scope.codeEditor.getValue();
                oldNode.cursor = $scope.codeEditor.getCursorPosition();
            }

            $scope.selected = node;
            if (node.type === "file") {
                $scope.codeEditor.setValue(node.code, 1); // moves cursor to the end
                var cursor = node.cursor == undefined ? {column: 0, row: 0} : node.cursor;
                $scope.codeEditor.moveCursorToPosition(cursor);
                $scope.codeEditor.focus();
            }
        };


        self.pathAsString = function (node) {
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
        };

        $scope.addNode = function (typeToCreate, node) {
            var nodeName = prompt("Enter " + typeToCreate + " name", typeToCreate + " name");
            if (nodeName == null || nodeName.trim() === "") {
                return;
            }

            function newNodeUUID() {
                return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                    var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                    return v.toString(16);
                });
            }

            function createNewNode() {
                var newNode = new TreeModel().parse({
                    id: newNodeUUID(),
                    name: nodeName,
                    type: typeToCreate,
                    children: []
                });
                if (typeToCreate === "file") {
                    newNode.model.code = "";
                }
                return newNode;
            }

            var nodeFound = self.findById(node.id);
            var newNode = createNewNode();

            if (newNode.model.type === "file") {
                nodeFound.parent.addChild(newNode);
                if (nodeName.indexOf(".java") > -1) {
                    $scope.javaClasses.push({
                        id: newNode.model.id,
                        name: newNode.model.name,
                        path: self.pathAsString(newNode)
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

            var nodeFound = self.findById(node.id);
            nodeFound.model.name = nodeName;

            function updatePaths(node) {
                if (node.model.type === "file" && node.model.name.indexOf(".java") > -1) {
                    var classesArray = $scope.javaClasses;
                    for (var i = 0; i < classesArray.length; ++i) {
                        if (classesArray[i].id == node.model.id) {
                            classesArray[i].name = node.model.name;
                            classesArray[i].path = self.pathAsString(node);
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

            var nodeFound = self.findById(node.id);
            if (nodeFound.model.name.indexOf(".java") > -1) {
                var classesArray = $scope.javaClasses;
                for (var index = 0; index < classesArray.length; ++index) {
                    if (classesArray[index].id == nodeFound.model.id) {
                        classesArray.splice(index, 1);
                    }
                }
            }

            $scope.codeEditor.getSession().setValue("");
            $scope.selected = nodeFound.parent.model;
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

        /**
         * Autocompletion
         */
        $scope.selectedFile = function (selected) {
            if (selected == undefined) {
                $scope.appModel.runnableNode.id = undefined;
                return;
            }

            var nodeFound = self.findById(selected.originalObject.id);
            $scope.appModel.runnableNode.id = nodeFound.model.id;
        };

    }

})();