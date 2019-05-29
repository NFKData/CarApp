'use strict'

mainModule
    .component('myHeader', {
        templateUrl: 'my-header/my-header.template.html',
        controller: ['$scope', function MyHeaderController($scope) {
            var self = this;
        }]
    })
