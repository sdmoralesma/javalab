(function () {
    'use strict';

    angular.module('LabApp', ['ngRoute', 'blockUI', 'treeControl', "angucomplete-alt"]);

    angular.module('LabApp').run(LabAppRun);

    LabAppRun.$inject = ['$rootScope', 'middleService'];

    function LabAppRun($rootScope, middleService) {

    }

})();