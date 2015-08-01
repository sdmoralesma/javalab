labApp.factory('middleClientService', function ($http) {

    const ENDPOINT = 'rest/process';
    return {
        runCode: function (model) {
            const RUN_CODE_SERVICE = ENDPOINT + "/run";
            $http.post(RUN_CODE_SERVICE, model, {
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
                headers: {'Content-Type': 'application/json'}
            }).then(function (response) {
                model.console = response.data;
            }, function error(failure) {
                alert('error code: ' + failure.status);
            });
        }
    };
});