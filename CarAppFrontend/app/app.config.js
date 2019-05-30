'use strict';

var env = {};

if (window) {
  Object.assign(env, window.__env);
}

app.config(['$routeProvider', '$locationProvider',
  function ($routeProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider
      .when("/", {
        templateUrl: 'main-content.template.html'
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

app.constant('__env', env);
