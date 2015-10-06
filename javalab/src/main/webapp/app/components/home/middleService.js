(function () {
    'use strict';

    angular.module('LabApp').factory('middleService', middleService);

    middleService.$inject = ['$http'];

    function middleService($http) {

        var ENDPOINT = 'rest/process';
        return {
            runCode: function (model) {
                $http.post(ENDPOINT + "/run", model)
                    .then(function (response) {
                        model.console = response.data.output;
                    }, function error(failure) {
                        alert('error code: ' + failure.status);
                    });
            },

            runTest: function (model) {
                $http.post(ENDPOINT + "/tests", model)
                    .then(function (response) {
                        model.console = response.data.output;
                    }, function error(failure) {
                        alert('error code: ' + failure.status);
                    });
            },

            saveWorkspace: function (model) {
                $http.post(ENDPOINT + "/save", model)
                    .then(function (response) {
                        model.console = response.data.output;
                    }, function error(failure) {
                        alert('error code: ' + failure.status);
                    });
            },

            initWorkspace: function () {
                return $http.get(ENDPOINT + "/init");
            },

            base62Workspace: function (base62Param) {
                return $http.get(ENDPOINT + "/" + base62Param);
            },

            newWorkspace: function () {
                return $http.get(ENDPOINT + "/new");
            }
        };
    }

})();