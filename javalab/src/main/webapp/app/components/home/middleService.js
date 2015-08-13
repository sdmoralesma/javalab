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

        newWorkspace: function (model) {
            const NEW_SERVICE = ENDPOINT + "/new";
            $http.get(NEW_SERVICE).then(function (response) {
                model.console = response.data.output;
            }, function error(failure) {
                alert('error code: ' + failure.status);
            });
        }
    };
}]);