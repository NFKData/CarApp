'use strict'

const DATE_DISPLAY_FORMAT = 'DD/MM/YYYY HH:mm:ss';

carDialog.component('carDialog', {
    templateUrl: 'car-dialog/car-dialog.template.html',
    controller: ['$rootScope', '$scope', 'moment', 'carService', function CarDialogController($rootScope, $scope, moment, carService) {
        let self = this;
        $('.carDialog').on('show.bs.modal', _ => {
            self.car = $rootScope.car;
            if (self.car.registration) {
                self.car.registration = moment(self.car.registration, moment.HTML5_FMT.DATETIME_LOCAL_MS).format(DATE_DISPLAY_FORMAT);
            }
            self.type = $rootScope.dialogType;
        })

        $('.carDialog').on('hidden.bs.modal', _ => {
            if (!$scope.form.$valid) {
                $scope.form.$setPristine();
                $scope.form.$setUntouched();
            }
        });

        self.registrationChange = (newValue, _) => {
            self.registration = moment(newValue, DATE_DISPLAY_FORMAT).format(moment.HTML5_FMT.DATETIME_LOCAL_MS);
        };

        self.update = _ => {
            if ($scope.form.$valid) {
                self.car.registration = self.registration;
                carService.update(self.car).then(_ => {
                    self.refreshCarList();
                    self.hide();
                }, response => {
                    if (response.status == 404) {
                        $rootScope.carId = self.car.id;
                        showCarNotFoundDialog();
                    } else if (response.status == 400) {
                        showValidationErrorDialog();
                    }
                    self.refreshCarList();
                });
            } else {
                showValidationErrorDialog();
            }
        };

        self.delete = _ => {
            carService.delete(self.car.id).then(_ => {
                self.refreshCarList();
                self.hide();
            }, response => {
                if (response.status == 404) {
                    $rootScope.carId = self.car.id;
                    showCarNotFoundDialog();
                }
                self.refreshCarList();
            });
        };

        self.create = _ => {
            if ($scope.form.$valid) {
                self.car.registration = self.registration;
                carService.create(self.car).then(_ => {
                    self.refreshCarList();
                    self.hide();
                }, response => {
                    if (response.status == 400) {
                        showValidationErrorDialog();
                    }
                });
            } else {
                showValidationErrorDialog();
            }
        };

        self.refreshCarList = _ => {
            $rootScope.refreshCars();
        };

        self.hide = _ => {
            $('.carDialog').modal('hide');
        }
    }]
});
