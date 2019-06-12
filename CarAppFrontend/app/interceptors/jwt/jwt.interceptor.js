'use strict'

jwtInterceptor.factory('jwtInterceptor', ['$q', 'authService', '$window', '$rootScope', function ($q, authService, $window, $rootScope) {
    const interceptor = new Object();

    interceptor.request = config => {
        if ($rootScope.jwt) {
            config.headers.Authorization = 'Bearer ' + $rootScope.jwt;
        }
        return config;
    }

    interceptor.responseError = rejection => {
        showMustLoginDialog();
        return $q.reject(rejection);
    }

    return interceptor;
}])
