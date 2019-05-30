warningDialog.directive('warningDialog', function () {
    return {
        restrict: 'E',
        scope: {
            warningMessage: '@'
        },
        templateUrl: 'warning-dialog/warning-dialog.template.html'
    }
});
