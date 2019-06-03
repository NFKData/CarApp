'use strict';

var env = {};

if (window) {
  Object.assign(env, window.__env);
}

app.config(['$routeProvider', '$locationProvider',
  function ($routeProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    $routeProvider
      .when('/', {
        redirectTo: '/carCards'
      })
      .when('/carCards', {
        templateUrl: 'views/car-cards-view.template.html'
      })
      .when('/notfound', {
        templateUrl: '404.html'
      })
      .otherwise('/notfound');
  }
]
);

app.constant('__env', env);

app.run(amMoment => {
  amMoment.changeLocale('en');
});

const showCarNotFoundDialog = _ => {
  $($('#idNotFound').children()[0]).modal();
}

const showValidationErrorDialog = _ => {
  $($('#noValidCar').children()[0]).modal();
}

const showNoCarsDialog = _ => {
  $($('#noCarsDialog').children()[0]).modal();
}

const showCarDialog = (type, id) => {
  $('.carDialog').modal();
}
