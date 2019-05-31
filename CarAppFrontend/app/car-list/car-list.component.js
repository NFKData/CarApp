'use strict'

carList.component('carList', {
    templateUrl: 'car-list/car-list.template.html',
    controller: ['carService', '$rootScope', '__env', function CarListController(carService, $rootScope, __env) {
        var self = this;
        self.carList = new Array();

        self.deleteCars = function () {
            while (self.carList.length > 0) {
                self.carList.pop();
            }
        }

        self.obtainCarList = function () {
            carService.retrieve(_ => {
                self.deleteCars();
                $('#warningDialog').modal()
            }, cars => {
                self.deleteCars();
                cars.forEach(car => {
                    self.carList.push(car);
                })
            });
        }
        self.obtainCarList();

        carService.updateCallback = self.obtainCarList;

        self.openCarDialog = function (type, id) {
            $rootScope.dialogType = type;
            if (id) {
                carService.retrieveById(id, _ => {
                    $('warning-dialog').attr('warning-message', 'Car with id ' + id + ' not found');
                    $('#warningDialog').modal();
                    $('warning-dialog').attr('warning-message', 'There\'s no car in the system.');
                },
                    car => {
                        $rootScope.car = car;
                        $('.carDialog').modal();
                    });
            } else {
                $rootScope.car = { country: '', brand: '', registration: '' };
                $('.carDialog').modal();
            }
        }
    }]
})
