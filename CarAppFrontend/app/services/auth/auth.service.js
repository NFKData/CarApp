'use strict'

authService.factory('authService', ['$rootScope', function ($rootScope) {
    const service = new Object();

    service.redirectTo = '/';

    service.launchLogin = _ => {
        webAuth.authorize();
    };

    service.handleCallbackResponse = response => {
        if (response && response.access_token) {
            $rootScope.jwt = response.access_token;
        } else {
            showMustLoginDialog();
        }
    }

    return service;
}])
