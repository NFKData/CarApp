'use strict'

carTable.component('carTable', {
    templateUrl: 'car-table/car-table.template.html',
    controller: ['DTOptionsBuilder', 'DTColumnBuilder', '$scope', '$rootScope', '$compile', 'carService', function CarTableController(DTOptionsBuilder, DTColumnBuilder, $scope, $rootScope, $compile, carService) {
        let self = this;

        self.carTable = {};

        self.actionsHtml = (data, type, full) => {
            return "<button ng-click=\"$ctrl.openCarDialog('delete', '" + data + "')\" class=\"btn btn-danger float-right btn-margin-left\">Delete</button>"
                + "<button ng-click=\"$ctrl.openCarDialog('update', '" + data + "')\" class=\"btn btn-info float-right btn-margin-left mr-2\">Detail</button>";
        }

        self.formatRegistration = (data, type, full) => {
            return moment(data, moment.HTML5_FMT.DATETIME_LOCAL_MS).format('DD/MM/YYYY HH:mm:ss');
        }

        self.fnRowCallback = (nRow, aData, iDisplayIndex, iDisplayIndexFull) => {
            $compile(nRow)($scope);
        };

        self.dtOptions = DTOptionsBuilder.fromFnPromise(_ => {
            return carService.retrieve()
        }).withDataProp('data').withOption('fnRowCallback', self.fnRowCallback);

        self.dtColumns = [
            DTColumnBuilder.newColumn('id').withTitle('ID'),
            DTColumnBuilder.newColumn('country').withTitle('Country'),
            DTColumnBuilder.newColumn('brand').withTitle('Brand'),
            DTColumnBuilder.newColumn('registration').withTitle('Registration').renderWith(self.formatRegistration),
            DTColumnBuilder.newColumn('id').withTitle('Actions').renderWith(self.actionsHtml)
        ];

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
        }

        $rootScope.refreshCars = _ => {
            self.carTable.reloadData();
        }
    }]
});
