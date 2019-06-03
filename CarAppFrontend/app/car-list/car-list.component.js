'use strict'

carList.component('carList', {
    templateUrl: 'car-list/car-list.template.html',
    controller: ['carService', '$scope', '$rootScope', '__env', function CarListController(carService, $scope, $rootScope, __env) {
        let self = this;
        $rootScope.carList = undefined;

        self.obtainCarList = _ => {
            carService.retrieve().then(response => {
                if (response.data.length > 0) {
                    $rootScope.carList = response.data;
                } else {
                    showNoCarsDialog();
                }
            });
        };

        self.openCarDialog = (type, id) => {
            $rootScope.dialogType = type;
            if (id) {
                carService.retrieveById(id).then(response => {
                    $rootScope.car = response.data;
                    showCarDialog();
                }, _ => {
                    $rootScope.carId = id;
                    showCarNotFoundDialog();
                    self.obtainCarList();
                });
            } else {
                $rootScope.car = { country: '', brand: '', registration: '' };
                showCarDialog();
            }
        };

        self.obtainCarList();
    }]
});
