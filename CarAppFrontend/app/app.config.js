'use strict';

var env = {};

if (window) {
  Object.assign(env, window.__env);
}

app.config(['$routeProvider', '$locationProvider', '$httpProvider',
  function ($routeProvider, $locationProvider, $httpProvider) {
    $routeProvider
      .when('/', {
        redirectTo: '/carCards'
      })
      .when('/carCards', {
        templateUrl: 'views/car-cards-view.template.html'
      })
      .when('/carTable', {
        templateUrl: 'views/car-table-view.template.html'
      })
      .when('/notfound', {
        templateUrl: '404.html'
      })
      .when('/callback', {
        templateUrl: 'views/callback-view.template.html'
      })
      .otherwise('/notfound');

    $locationProvider.hashPrefix('');
    $locationProvider.html5Mode(true);

    $httpProvider.interceptors.push('jwtInterceptor')
  }
]
);

app.constant('__env', env);

app.run(amMoment => {
  amMoment.changeLocale('en');
});

const showCarNotFoundDialog = _ => {
  showWarningDialog('idNotFound');
}

const showValidationErrorDialog = _ => {
  showWarningDialog('noValidCar');
}

const showNoCarsDialog = _ => {
  showWarningDialog('noCarsDialog');
}

const showCarDialog = _ => {
  $('.carDialog').modal();
}

const showMustLoginDialog = _ => {
  showWarningDialog('mustLogin');
}

const showWarningDialog = id => {
  $($('#' + id + '').children()[0]).modal();
}

const webAuth = new auth0.WebAuth({
  domain: 'everis-carapp.eu.auth0.com',
  clientID: 'rrDYabEzebUPsoDSsRdlxmd5MESQPAck',
  responseType: 'token',
  redirectUri: 'http://localhost:8000/callback',
  audience: 'https://everis-carapp.herokuapp.com/car-api'
});
