'use strict';


function routeConfig ($routeProvider, $locationProvider) {
  $routeProvider
    .when('/', {
      templateUrl: 'home/welcome.html',
      controller: 'WelcomeCtrl'
    })
    .when('/mensagens', {
      templateUrl: 'mensagens/mensagem_lista.html',
      controller: 'MensagemListaCtrl'
    })
    .otherwise({
      redirectTo: '/'
    });

    // $locationProvider.html5Mode(true);
};


angular
  .module('governaPush')
  .config(routeConfig);


