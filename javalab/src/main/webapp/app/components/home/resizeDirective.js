angular.module('LabApp').directive('resize', ['$window', function ($window) {

    function resizeAreasVertically() {
        var minWidthDesktop = 980;
        if ($(window).width() < minWidthDesktop) {
            return;
        }

        var windowHeight = angular.element(window).height();
        var extNavHeight = $('#ext-nav').height();
        //  Define height for each element based on %
        var codeEditorHeight = (windowHeight * 75 / 100) - extNavHeight;
        var consoleHeight = (windowHeight * 25 / 100) - extNavHeight;

        // resize elements
        $('#code-editor').height(codeEditorHeight);
        $('#console').height(consoleHeight);
    }

    return {
        link: function (scope) {
            angular.element($window).on('resize', function (e) {
                resizeAreasVertically();
                scope.$broadcast('resize::resize');
            });

            scope.$on('resize', function (event, data) {
                resizeAreasVertically();
            });
        }
    }
}]);