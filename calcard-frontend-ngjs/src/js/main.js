angular.module('calcard', ['ngRoute'])
    .config(function ($routeProvider, $locationProvider) {

        $locationProvider.html5Mode(true);

        $routeProvider.when('/cliente', {
            templateUrl: 'partials/cliente.html',
            controller: 'ClienteController'
        });

        $routeProvider.otherwise({
            redirectTo: '/cliente'
        });
    });