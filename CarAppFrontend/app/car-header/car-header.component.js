'use strict'

mainModule
    .component('carHeader', {
        templateUrl: 'car-header/car-header.template.html',
        controller: ['authService', function CarHeaderController(authService) {
            var self = this;
            self.login = _ => {
                authService.launchLogin();
            }
        }]
    })
