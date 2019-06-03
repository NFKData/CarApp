'use strict'

const SUB_URL = '/cars'

carService.factory('carService', ['$http', '__env', '$rootScope', function ($http, __env, $rootScope) {
    const service = new Object();

    service.retrieve = function () {
        return $http.get(__env.apiUrl + SUB_URL);
    };

    service.retrieveById = function (id, notFoundCallback, foundCallback) {
        return $http.get(__env.apiUrl + SUB_URL + '/' + id);
    }

    service.update = function (car, carNotFoundCallback) {
        return $http.put(__env.apiUrl + SUB_URL + '/' + car.id, car);
    }

    service.delete = function (id, carNotFoundCallback) {
        return $http.delete(__env.apiUrl + SUB_URL + '/' + id);
    }

    service.create = function (car) {
        return $http.post(__env.apiUrl + SUB_URL, car);
    }

    return service;
}]);
