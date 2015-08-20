'use strict';
labApp.factory('middleService', ['$http', function ($http) {

    const ENDPOINT = 'rest/process';
    return {
        runCode: function (model) {
            const RUN_CODE_SERVICE = ENDPOINT + "/run";
            $http.post(RUN_CODE_SERVICE, model, {
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data.output;
            }, function error(failure) {
                alert('error code: ' + failure.status);
            });
        },

        runTest: function (model) {
            const RUN_TESTS_SERVICE = ENDPOINT + "/tests";
            $http.post(RUN_TESTS_SERVICE, model, {
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data.output;
            }, function error(failure) {
                alert('error code: ' + failure.status);
            });
        },

        saveWorkspace: function (model) {
            const SAVE_SERVICE = ENDPOINT + "/save";
            $http.post(SAVE_SERVICE, model, {
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data.output;
            }, function error(failure) {
                alert('error code: ' + failure.status);
            });
        },

        initWorkspace: function () {
            const INIT_SERVICE = ENDPOINT + "/init";
            return $http.get(INIT_SERVICE);
        },

        base62Workspace: function (base62Param) {
            const BASE62_SERVICE = ENDPOINT + "/" + base62Param;
            return $http.get(BASE62_SERVICE);
        },

        newWorkspace: function () {
            const NEW_SERVICE = ENDPOINT + "/new";
            return $http.get(NEW_SERVICE);
        }
    };
}]);