'use strict';


function WelcomeCtrl($scope) {

  $scope.titulo = 'Ola Controller';



}

angular
  .module('governaPush')
  .controller('WelcomeCtrl', WelcomeCtrl);
