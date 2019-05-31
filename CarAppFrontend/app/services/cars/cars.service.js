'use strict'

const SUB_URL = '/cars'

carService.factory('carService', ['$http', '__env', function ($http, __env) {
    var service = new Object();
    //Must be initialized by controllers
    service.updateCallback = undefined;

    service.retrieve = function (noCarsCallback, successCallback) {
        $http.get(__env.apiUrl + SUB_URL).then(function (response) {
            if (response.data.length == 0) {
                if (noCarsCallback) { noCarsCallback(); }
            } else {
                successCallback(response.data);
            }
        });
    };

    service.retrieveById = function (id, notFoundCallback, foundCallback) {
        $http.get(__env.apiUrl + SUB_URL + '/' + id).then(response => {
            if (response.status == 404) {
                notFoundCallback();
            } else if (response.status == 200) {
                foundCallback(response.data);
            }
        });
    }

    service.update = function (car) {
        $http.put(__env.apiUrl + SUB_URL + '/' + car.id, car).then(response => {
            if (service.updateCallback) {
                service.updateCallback(response.data)
            }
        });
    }

    service.delete = function (id) {
        $http.delete(__env.apiUrl + SUB_URL + '/' + id).then(_ => {
            if (service.updateCallback) {
                service.updateCallback();
            }
        })
    }

    service.create = function (car) {
        $http.post(__env.apiUrl + SUB_URL, car).then(response => {
            if (service.updateCallback) {
                service.updateCallback();
            }
        })
    }

    return service;
}]);
