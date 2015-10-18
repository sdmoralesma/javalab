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
                    }, function (failure) {
                        alert('error code: ' + failure.status);
                    });
            },

            runTest: function (model) {
                $http.post(ENDPOINT + "/tests", model)
                    .then(function (response) {
                        model.console = response.data.output;
                    }, function (failure) {
                        alert('error code: ' + failure.status);
                    });
            },

            saveWorkspace: function (model) {
                $http.post(ENDPOINT + "/save", model)
                    .then(function (response) {
                        model.console = response.data.output;
                    }, function (failure) {
                        alert('error code: ' + failure.status);
                    });
            },

            initWorkspace: function (lang) {
                return $http.get(ENDPOINT + "/init/" + lang);
            },

            workspaceByLabId: function (labId) {
                return $http.get(ENDPOINT + "/" + labId);
            },

            newWorkspace: function () {
                return $http.get(ENDPOINT + "/new");
            }
        };
    }

})();