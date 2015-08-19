labApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'app/components/partials/test.html',
            controller: "HomeCtrl",
            resolve: {
                mylocaldata: ["middleService", function (middleService) {
                    return middleService.newWorkspace();
                }]
            }

        })
        //.when('/data/:idBase62', {
        //    resolve: {
        //        localdata: ["middleService", function (middleService) {
        //            return middleService.newWorkspace();
        //        }]
        //    }
        //})
        .otherwise({
            redirectTo: '/'
        });
}]);