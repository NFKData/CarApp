const SUB_URL = '/cars'

carList.component('carList', {
    templateUrl: 'car-list/car-list.template.html',
    controller: ['$http', '__env', function CarListController($http, __env) {
        var self = this;
        self.carList = undefined;
        self.getCarList = function () {
            $http.get(__env.apiUrl + SUB_URL).then(function (response) {
                if (response.data.length == 0) {
                    $('#warningDialog').modal();
                } else {
                    self.carList = response.data.length > 0 ? response.data : undefined;
                }
            });
        };

        self.notImplemented = function () {
            alert("This function is not implemented");
        };

        self.getCarList();
    }]
})
