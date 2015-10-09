(function () {
    'use strict';

    angular.module('LabApp').config(LabAppConfig);

    LabAppConfig.$inject = ['$routeProvider'];

    function LabAppConfig($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'app/components/partials/main-interface.html',
                controller: 'HomeCtrl',
                resolve: {
                    initData: ["middleService", function (middleService) {
                        return middleService.initWorkspace('java');
                    }]
                }

            })
            .when('/:lang', {
                templateUrl: 'app/components/partials/main-interface.html',
                controller: 'HomeCtrl',
                resolve: {
                    initData: ['middleService', '$route', function (middleService, $route) {
                        return middleService.initWorkspace($route.current.params.lang);
                    }]
                }

            })
            .when('/data/:idBase62', {
                templateUrl: 'app/components/partials/main-interface.html',
                controller: 'HomeCtrl',
                resolve: {
                    initData: ['middleService', '$route', function (middleService, $route) {
                        return middleService.base62Workspace($route.current.params.idBase62);
                    }]
                }
            })
            .otherwise({
                redirectTo: '/'
            });

    }

})();