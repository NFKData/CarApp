'use strict'

var app = angular.module('CarAppFrontend', [
  'ngRoute',
  'angularMoment',
  'moment-picker',
  'auth0.auth0',
  'carHeader',
  'carList',
  'warningDialog',
  'carDialog',
  'carTable',
  'authCallback',
  'carServiceProvider',
  'authServiceProvider',
  'jwtInterceptorProvider'
]);
