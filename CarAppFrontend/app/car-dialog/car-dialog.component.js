'use strict'

const DATE_DISPLAY_FORMAT = 'DD/MM/YYYY HH:mm:ss';

carDialog.component('carDialog', {
    controller: ['$rootScope', 'moment', 'carService', function CarDialogController($rootScope, moment, carService) {
        var self = this;
        $('.carDialog').on('show.bs.modal', _ => {
            self.car = $rootScope.car;
            $rootScope.car = undefined;
            if (self.car.registration) {
                self.car.registration = moment(self.car.registration, moment.HTML5_FMT.DATETIME_LOCAL_MS).format(DATE_DISPLAY_FORMAT);
            }
            self.type = $rootScope.dialogType;
            $rootScope.dialogType = undefined;
        })

        self.registrationChange = function (newValue, oldValue) {
            self.registration = moment(newValue, DATE_DISPLAY_FORMAT).format(moment.HTML5_FMT.DATETIME_LOCAL_MS);
        }

        self.update = function () {
            self.car.registration = self.registration;
            carService.update(self.car);
        }

        self.delete = function () {
            carService.delete(self.car.id);
        }

        self.create = function () {
            self.car.registration = self.registration;
            carService.create(self.car);
            self.registration = undefined;
            self.car = undefined;
        }
    }],
    templateUrl: 'car-dialog/car-dialog.template.html',
});
