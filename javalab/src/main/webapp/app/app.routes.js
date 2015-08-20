labApp.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'app/components/partials/main-interface.html',
            controller: "HomeCtrl",
            resolve: {
                initData: ["middleService", function (middleService) {
                    return middleService.initWorkspace();
                }]
            }

        })
        .when('/data/:idBase62', {
            resolve: {
                localdata: ["middleService", "$route", function (middleService, $route) {
                    return middleService.base62Workspace($route.current.params.idBase62);
                }]
            }
        })
        .otherwise({
            redirectTo: '/'
        });

    $locationProvider.html5Mode(true);
}]);