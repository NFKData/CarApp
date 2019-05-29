'use strict';

app.config(['$routeProvider', '$locationProvider',
  function ($routeProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider
      .when("/", {
        template: '<h1>Main Content</h1>'
      }
      )
      .when("/notfound", {
        templateUrl: '404.html'
      }
      )
      .otherwise({ redirectTo: '/notfound' });
  }
]
);
