var labApp = angular.module('LabApp', ['blockUI', 'treeControl', "angucomplete-alt"]);

labApp.run(['middleService', function (middleService) {
    middleService.initialize();
}]);