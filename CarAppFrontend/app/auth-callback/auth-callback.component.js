'use strict'

authCallback.component('authCallback', {
    controller: ['authService', '$location', '$window', function AuthCallbackController(authService, $location, $window) {
        $location.url($location.url().replace('callback#', 'callback?'));
        authService.handleCallbackResponse($location.search());
    }]
});
